import { Component, OnInit } from '@angular/core';
import { SideNavbarComponent } from "../../../admin/components/side-navbar/side-navbar.component";
import { SearchBarComponent } from "../../components/search-bar/search-bar.component";
import { FilterDropdownComponent } from "../../components/filter-dropdown/filter-dropdown.component";
import { UserTableComponent } from "../../components/user-table/user-table.component";
import { UserManagementService } from '../../services/UserManagamentService';
import { BehaviorSubject, combineLatest, debounceTime, filter, map, Observable, startWith, switchMap } from 'rxjs';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { User } from '../../../models/User';
import { FiltersDTO } from '../../../models/FiltersDTO';
import { PaginationComponent } from "../../components/pagination/pagination.component";
import { DateFilterComponent } from "../../../shared/components/date-filter/date-filter.component";

@Component({
  selector: 'app-user-management',
  imports: [SearchBarComponent, FilterDropdownComponent, UserTableComponent, ReactiveFormsModule, CommonModule, PaginationComponent, DateFilterComponent],
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.css'
})
export class UserManagementComponent implements OnInit {

  users: User[] = [];
  filteredUsers$: BehaviorSubject<User[]> ;

  filters$ : BehaviorSubject<FiltersDTO | null>;
  pagination$ :  BehaviorSubject<{page : number, size : number}>
  searchKeyword$ : BehaviorSubject<string>

  totalPages : number = 0;
  currentPage : number = 1;

  constructor(
    private userManagementService: UserManagementService,
    private toastr: ToastrService
  ) {
    this.filteredUsers$ = new BehaviorSubject<User[]>([]);
    this.filters$ = new BehaviorSubject<FiltersDTO | null>(null);
    this.pagination$ = new BehaviorSubject<{page : number ; size : number}>({page : 0 , size : 2 });
    this.searchKeyword$ = new BehaviorSubject<string>("");
  }

  ngOnInit(): void {
    this.initReactiveDataPipeline();
  }

  private initReactiveDataPipeline(): void {
    combineLatest([this.filters$, this.pagination$, this.searchKeyword$])
      .pipe(
        debounceTime(300),
        switchMap(([filters, pagination, keyword]) =>
          this.userManagementService.fetchUsers({
            ...filters,
            page: pagination.page,
            size: pagination.size,
            keyword,
          })
        )
      )
      .subscribe({
        next: (response) => {
          this.users = response.content;
          this.totalPages = response.totalPages;
          this.currentPage = response.pageNumber+1;
        },
        error: (err) => {
          this.toastr.error(err.message || 'Failed to load users', 'Error');
        },
      });
  }

  updatePartialFilter(partial : Partial<FiltersDTO>){
    this.filters$.next({...this.filters$.value,...partial});
    this.pagination$.next({page : 0,size :2});
    
  }

  onPageChange(page: number): void {
    const current = this.pagination$.value;
    page = page-1;
    this.pagination$.next({ ...current, page });
  }

  onPageSizeChange(size: number): void {
    const current = this.pagination$.value;

    //Added to reset the pagination
    this.pagination$.next({ ...current, size });
  }

  onSearchChange(keyword: string): void {
    this.searchKeyword$.next(keyword);
    
    //Added to reset the pagination
    this.pagination$.next({page : 0,size :2});
  }

}

