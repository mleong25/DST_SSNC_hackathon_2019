import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatSelectChange } from '@angular/material';
import { FormGroup, FormArray } from '@angular/forms';
import { RequestType } from '../../models';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.css']
})
export class RequestComponent implements OnInit {
  @Input('command') commandForm;
  @Output() newRequest = new EventEmitter<any>();

  requestTypes = [
    'GET',
    'POST',
    'PUT',
    'DELETE'
  ];

  constructor() { }

  ngOnInit() {
  }

  addRequest() {
    this.newRequest.emit();
  }

  removeRequest(index: number) {
    const fa = this.commandForm.controls.requests as FormArray;
    fa.removeAt(index);
  }

  getErrorMessage(index: number, controlname: string) {
    const fc = ((this.commandForm.controls.requests as FormArray).at(index) as FormGroup).get(controlname);
    const requiredType = (controlname === 'name') ? 'name' : 'URL';

    return fc.hasError('required') ? 'You must enter a ' + requiredType :
           fc.hasError('noDuplicates') ? 'No duplicate variable names' :
           fc.hasError('validName') ? 'Enter a valid variable name' : '';
  }

  canHaveBody(event: MatSelectChange, request: FormGroup) {
    const method = event.value;

    if (method === RequestType.POST || method === RequestType.PUT) {
      request.get('body').enable();
    } else {
      request.get('body').disable();
    }
  }
}