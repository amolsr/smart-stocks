import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup-modal',
  templateUrl: './signup-modal.component.html',
  styleUrls: ['./signup-modal.component.css']
})
export class SignupModalComponent implements OnInit {


  signupForm: FormGroup = new FormGroup({});

  constructor(private _fb: FormBuilder, public dialogRef: MatDialogRef<SignupModalComponent>, private userService: UserService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.signupForm = this._fb.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      phoneNo: [null, [Validators.required]],
      dob: [null, [Validators.required]],
      gender: [null, [Validators.required]],
      email: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  onSubmit() {
    console.log(this.signupForm.value);
    this.userService.signup(this.signupForm.value).subscribe(
      data => {
        this.toastr.success("Signup Successfull!", "", {
          closeButton: true,
          "positionClass": "toast-bottom-right",
        })
        this.dialogRef.close();
      },
      error => {
        console.log('Something went wrong !')
        this.toastr.error(error.error.errors.error, "", {
          closeButton: true,
          "positionClass": "toast-bottom-right",
        })
      }
    );
  }

}
