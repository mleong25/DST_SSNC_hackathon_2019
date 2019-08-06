import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { Command } from '../../models';

function validCommandName(c: FormControl) {
  return /^[a-zA-Z][_0-9a-zA-Z]*$/.test(c.value) ? null : { 
    validName: {
      valid: false
    }
  };
}

@Component({
  selector: 'app-command-info',
  templateUrl: './command-info.component.html',
  styleUrls: ['./command-info.component.css']
})
export class CommandInfoComponent {
  @Input('command') commandInfoForm: FormGroup;

  constructor(private fb: FormBuilder) { }

  getErrorMessage() {
    return this.commandInfoForm.controls.name.hasError('required') ? 'You must enter a name' :
           this.commandInfoForm.controls.name.hasError('validName') ? 'Enter a valid name' :
           this.commandInfoForm.controls.name.hasError('noDuplicates') ? 'No duplicate commands' : '';
  }
}