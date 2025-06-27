import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import {ImageCroppedEvent,ImageCropperComponent} from 'ngx-image-cropper'

@Component({
  selector: 'app-profile-upload',
  imports: [CommonModule,ImageCropperComponent],
  templateUrl: './profile-upload.component.html',
  styleUrl: './profile-upload.component.css'
})
export class ProfileUploadComponent {
  @Input() control? : FormControl;
  @Output() fileSelected = new EventEmitter<File>();

  imageChangedEvent: Event | null = null;
  croppedImagePreview: string = '';
  croppedImage: string = '';
  errorMessage = '';



  onFileChange(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    this.control?.markAsTouched();
    if (!file) {
      this.errorMessage = 'No file selected'
      this.control?.setErrors({required : true});
      return
    };

    if (!file.type.startsWith('image/')) {
      this.errorMessage = 'Only image files are allowed.';
      this.control?.setErrors({invalidType : true});
      return;
    }

    if (file.size > 2 * 1024 * 1024) {
      this.errorMessage = 'File size must be under 2MB.';
      this.control?.setErrors({maxsize : true});
      return;
    }

    this.imageChangedEvent = event;
    this.control?.setErrors(null);
    this.croppedImagePreview = '';
    this.errorMessage = '';
  }

  onImageCropped(event: ImageCroppedEvent): void {
    this.croppedImagePreview = event.objectUrl || '';    
  }

  confirmCrop(): void {
    this.croppedImage = this.croppedImagePreview;
    this.imageChangedEvent = null;

    if (this.croppedImage) {
      this.blobUrlToFile(this.croppedImage, `profile_${this.generateUUID()}.jpg`).then(file => {
        this.fileSelected.emit(file);
        this.control?.setValue(file);
        this.control?.markAsTouched();
        this.control?.setErrors(null);
      })
      .catch(err =>{
        this.control?.setErrors({ processingError: true });
      })
    }
  }

  cancelCrop(): void {
    this.imageChangedEvent = null;
    this.croppedImagePreview = '';
      this.control?.reset();

  }

  private async blobUrlToFile(blobUrl: string, fileName: string): Promise<File> {
  const response = await fetch(blobUrl);
  const blob = await response.blob();
  return new File([blob], fileName, { type: blob.type });
}

generateUUID(): string {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

}
