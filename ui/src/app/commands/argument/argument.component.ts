import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormArray } from '@angular/forms';

@Component({
  selector: 'app-argument',
  templateUrl: './argument.component.html',
  styleUrls: ['./argument.component.css']
})
export class ArgumentComponent {
  @Input('command') commandForm: FormGroup;
  @Output() newArgument = new EventEmitter<any>();

  argTypes = ["String", "Number", "Boolean", "Channel Member", "JSON Object"];

  constructor() { }

  addArgument() {
    this.newArgument.emit();
  }

  removeArgument(index: number) {
    const fa = this.commandForm.controls.arguments as FormArray;
    fa.removeAt(index);
  }

  getErrorMessage(index: number, controlname: string) {
    const fc = ((this.commandForm.controls.arguments as FormArray).at(index) as FormGroup).get(controlname);

    return fc.hasError('required') ? 'You must enter a name' :
           fc.hasError('noDuplicates') ? 'No duplicate variable names' :
           fc.hasError('validName') ? 'Enter a valid variable name' : '';
  }
}
