import { Component } from '@angular/core';
import { AddGoalButtonComponent } from "./add-goal-button/add-goal-button.component";
import { Goal, GoalFilterOptions, GoalFormData, GoalStats } from '../../models/goal.model';
import { StatsCardComponent } from "./stats-card/stats-card.component";
import { SectionHeaderComponent } from "./section-header/section-header.component";
import { GoalCardComponent } from "./goal-card/goal-card.component";
import { CommonModule } from '@angular/common';
import { AddGoalModalComponent } from "../../modals/add-goal-modal/add-goal-modal.component";
import { EditGoalModalComponent } from "../../modals/edit-goal-modal/edit-goal-modal.component";
import { FeatureButtonComponent } from "../../components/feature-button/feature-button.component";
import { UserCategoryResponse } from '../../models/user-category.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';
import { UserGoalService } from '../../services/user-goal.service';
import { Observable } from 'rxjs';
import { ContributionFormData } from '../../models/contribution-form-date.model';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-goal-management',
  imports: [ StatsCardComponent, SectionHeaderComponent, GoalCardComponent, CommonModule, AddGoalModalComponent, EditGoalModalComponent, FeatureButtonComponent],
  templateUrl: './goal-management.component.html',
  styleUrl: './goal-management.component.css'
})
export class GoalManagementComponent {
  viewMode = "grid"
  searchQuery = ""
  filterOptions: GoalFilterOptions = {
    category: "",
    progress: "",
    sort: "recent",
  }
stats: GoalStats = {
    totalActiveGoals: 0,
    totalSaved: 0.00,
    completedGoals: 0
  };

  goalEditTimeInMin:number = environment.goalEditTimeInMin;



  categories : UserCategoryResponse[] = [];
  allGoals$ : Observable<Goal[]>;
  allGoals: Goal[] = [];

  constructor(private spinner : NgxSpinnerService,private toastr : ToastrService ,
    private userService :UserService,private goalService : UserGoalService,
    private dialog : MatDialog
  ){
      this.allGoals$= goalService.allGoals.asObservable();
      this.allGoals$.subscribe({
        next : (goals)=>{
          this.allGoals = goals;
        }
      })
      
  }

  isModalOpen: boolean = false;
  showEditModal: boolean = false;
  selectedGoal: Goal | null = null;
  ngOnInit() {
    this.goalService.goalStats.subscribe({
      next : (stats)=>{
        if(stats != null){
          this.stats = stats;
        }
      }
    })
    this.goalService.changeCycle.subscribe({
        next:()=>{
          this.loadCatgories();
          this.loadAllGoals();
          this.loadGoalSummary();
        }
      });
    this.loadCatgories();
    this.loadAllGoals();
    this.loadGoalSummary();
  }


  // get upcomingDeadlineGoals(): Goal[] {
  //   return this.goals.sort((a, b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());
  // }

  loadCatgories(){
    this.spinner.show();
    this.userService.getCategories().subscribe({
      next : (categories)=>{
        this.categories = categories.filter((category) => category.usageTypes.includes("GOAL"));
        this.spinner.hide();
      },
      error:(err)=>{
        this.spinner.hide();
        this.toastr.error("Failed to load categories");
      }
    })
  }

  loadAllGoals(){
    this.spinner.show();
    this.goalService.getAllIncomes().subscribe({
      next : ()=>{
        this.spinner.hide();
      },
      error:(err)=>{
        this.spinner.hide();
        this.toastr.error("Some issues while fetching goals");
      }
    })
  }

  loadGoalSummary(){
    this.spinner.show();
    this.goalService.goalSummary().subscribe({
      next : ()=>{
        this.spinner.hide();
      },
      error:(err)=>{
        this.spinner.hide();
        this.toastr.error("Failed during fetching goal stats");
      }
    })
  }



  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  }

  // Modal Methods
  openAddModal(): void {
    this.isModalOpen = true;
  }

  closeAddModal(): void {
    this.isModalOpen = false;
  }

  // Can edit the goal method
  canEditGoal(goal :Goal): boolean {
  if (!goal?.createdAt) return false;

  const createdAt = new Date(goal.createdAt).getTime();
  const now = Date.now();
  console.log(createdAt+" "+now);
  
  const diffInMinutes = (now - createdAt) / (1000 * 60);

  if (diffInMinutes > this.goalEditTimeInMin) {
    this.toastr.warning(`You can't edit this goal at the moment.`);
    return false;
  }

  return true;
  }


  openEditModal(goal: Goal): void {
    if(this.canEditGoal(goal)){
      this.selectedGoal = goal;
      this.showEditModal = true;
    }
    
  }

  closeEditModal(): void {
    this.showEditModal = false;
    this.selectedGoal = null;
  }

    // Goal Management
  onGoalAdded(goalData: GoalFormData): void {
    this.spinner.show()
    this.goalService.addNewGoal(goalData).subscribe({
      next : ()=>{
        this.spinner.hide();
        this.toastr.success("New goal added successfully");
      },
      error:(err)=>{
        this.spinner.hide();
        this.toastr.error(`Failed due to : ${err.message}`||"Something gone wrong");
      }
    });
  }

  onGoalUpdated(event: { id: number; data: GoalFormData }): void {
    this.spinner.show();
    this.goalService.editGoal(event).subscribe({
      next:(res)=>{
        this.spinner.hide();
          this.toastr.success("The goal edited successfully");
          // this.toastr.info("You cannot edit the goal now.")
      },
      error:(err)=>{
        console.log(err);
        this.spinner.hide();
        this.toastr.error("Failed during editing the goal");
      }
    })
    
  }



  onDeleteGoal(goal: Goal): void {
    const message = `
  <b>Are you sure you want to delete this goal?</b><br><br>
  <strong>• Title:</strong> ${goal.title}<br>
  <strong>• Target Amount:</strong> ${this.formatCurrency(goal.targetAmount)}<br>
  <strong>• Category:</strong> ${goal.category.name}<br>
  <strong>• Status:</strong> ${goal.status}<br><br>
  This action cannot be undone.
  `;

    this.dialog.open(ConfirmDialogComponent, {
        width: '400px',
        data: {
          title: 'Delete Goal?',
          message: message,
          confirmText: 'Delete',
          cancelText: 'Cancel'
        }
      }).afterClosed().subscribe({
        next: (confirmed) => {
          if (confirmed) {
            this.spinner.show();
            this.goalService.deleteGoal(goal.id).subscribe({
              next: () => {
                this.toastr.success('Goal deleted successfully');
                this.spinner.hide();
              },
              error: (err) => {
                this.toastr.error(`Failed to delete goal: ${err.errorCode}`);
                this.spinner.hide();
              }
            });
          }
        }
      });

  }

  onSeeAllSavingPlans(): void {
    console.log('See all saving plans');
  }

  onSeeAllDeadlines(): void {
    console.log('See all deadlines');
  }

  //filters and serach
  onViewModeChange(mode: string): void {
    console.log(mode);
    
    this.viewMode = mode
  }


  clearAllFilters(): void {
    this.searchQuery = ""
    this.filterOptions = {
      category: "",
      progress: "",
      sort: "recent",
    }
  }

  onSearch(query: string): void {
    this.searchQuery = query
  }

  onFilterChange(filters: GoalFilterOptions): void {
    this.filterOptions = { ...filters }
  }

  get hasActiveFilters(): boolean {
    return !!(this.filterOptions.category || this.filterOptions.progress || this.filterOptions.sort !== "recent")
  }

  trackByGoal(index: number, goal: Goal): number {
    return goal.id
  }

  get filteredGoals(): Goal[] {
    let filtered = [...this.allGoals]

    // Apply search filter
    if (this.searchQuery) {
      const query = this.searchQuery.toLowerCase()
      filtered = filtered.filter(
        (goal) => goal.title.toLowerCase().includes(query) || goal.category.name.toLowerCase().includes(query),
      )
    }

    // Apply category filter
    if (this.filterOptions.category) {
      filtered = filtered.filter((goal) => goal.category.name.toLocaleLowerCase() === this.filterOptions.category.toLocaleLowerCase())
    }

    // Apply progress filter
    if (this.filterOptions.progress) {
      const [min, max] = this.filterOptions.progress.split("-").map(Number)
      filtered = filtered.filter((goal) => {
        const progress = (goal.currentAmount / goal.targetAmount) * 100
        return progress >= min && progress <= max
      })
    }

    // Apply sorting
    filtered.sort((a, b) => {
      switch (this.filterOptions.sort) {
        case "progress":
          return b.currentAmount / b.targetAmount - a.currentAmount / a.targetAmount
        case "target":
          return b.targetAmount - a.targetAmount
        case "deadline":
          return new Date(a.endDate).getTime() - new Date(b.endDate).getTime()
        case "title":
          return a.title.localeCompare(b.title)
        default :
          return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();  
      }
    })

    return filtered
  }

  get filterCategories():string[]{
    return this.categories.map(category => category.name);
  }

   getSearchSummary(): string {
    const parts = []
    if (this.searchQuery) parts.push(`matching "${this.searchQuery}"`)
    if (this.filterOptions.category) parts.push(`in ${this.filterOptions.category}`)
    if (this.filterOptions.progress) parts.push(`with ${this.filterOptions.progress}% progress`)
    return parts.join(", ") || "showing all goals"
  }

  addContribution(contribution : ContributionFormData){
      
      this.spinner.show();
      this.goalService.addContribution(contribution).subscribe({
        next:()=>{
          this.spinner.hide();
          this.toastr.success("Contribution added successfully");
        }
        ,
        error:(err)=>{
          this.spinner.hide();
          this.toastr.error("Something gone wrong during adding contribution");
        }
      })
  }

  

}
