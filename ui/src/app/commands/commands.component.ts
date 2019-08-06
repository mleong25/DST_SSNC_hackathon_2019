import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Command, Argument, ArgumentType, Request, RequestType } from '../models';
import { FormControl, FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: 'app-commands',
  templateUrl: './commands.component.html',
  styleUrls: ['./commands.component.css']
})
export class CommandsComponent implements OnInit {
  inputcommands: Command[] = [];
  commandsForm: FormGroup;
  tabsIndex = 0;

  constructor(private fb: FormBuilder, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.commandsForm = this.fb.group({
      commands: this.fb.array(this.inputcommands.map(this.formFromCommand))
    });
  }

  ////// COMMANDS
  commands(): FormArray {
    return this.commandsForm.controls.commands as FormArray;
  }

  command(index: number): FormGroup {
    return ((this.commandsForm.controls.commands as FormArray).at(index !== null ? index : this.tabsIndex) as FormGroup);
  }

  addFirstCommand() {
    const command1 = new Command('command1', []);
    (this.commandsForm.get('commands') as FormArray).push(this.formFromCommand(command1));

    const tempcommand = new Command('+');
    (this.commandsForm.get('commands') as FormArray).push(this.formFromCommand(tempcommand));
  }

  addNewCommand() {
    const takenNames = this.commands().controls.map((command: FormGroup) => command.controls.name.value);
    let nameCounter = this.commands().length;

    while (takenNames.includes(`command${nameCounter}`)) {
      nameCounter++;
    }

    this.command(this.commands().length - 1).get('name').setValue(`command${nameCounter}`);
    this.commands().push(this.formFromCommand(new Command('+', [])));
  }

  selectTab(event: MatTabChangeEvent) {
    if (this.command(event.index).get('name').value === '+') {
      this.addNewCommand();
    }
  }

  formFromCommand = (command: Command): FormGroup => {
    return this.fb.group({
      name: this.fb.control(command.name, [Validators.required, this.validName, this.noDuplicateCommands]),
      desc: command.desc,
      arguments: this.fb.array(command.args.map(this.createArgumentForm)),
      requests: this.fb.array(command.requests),
      response: command.response
    });
  }

  getCommands(): Command[] {
    const controls = this.commands().controls.filter(control => control.get('name').value !== "+");
    const cmds = [];
    let index = 0;

    for (let cmd of controls) {
      const newCmd = new Command((cmd as FormGroup).get('name').value);

      newCmd.desc = (cmd as FormGroup).get('desc').value;
      newCmd.args = this.getArguments(index);
      newCmd.requests = this.getRequests(index++);
      newCmd.response = (cmd as FormGroup).get('response').value;

      cmds.push(newCmd);
    }

    return cmds;
  }

  removeCommand() {
    this.commands().removeAt(this.tabsIndex);
     if (this.command(this.tabsIndex).get('name').value === '+') this.tabsIndex--;
  }

  ////// ARGUMENTS

  args(index?: number): FormArray {
    return ((this.commandsForm.get('commands') as FormArray).at(index !== undefined ? index : this.tabsIndex) as FormGroup).get('arguments') as FormArray;
  }

  argumentFromForm(fc: FormGroup): Argument {
    return new Argument(fc.get('name').value).argtype(fc.get('argType').value).isarray(fc.get('isArray').value);
  }

  addArgument(index: number) {
    const fa = this.args(index);
    const takenNames = this.getArguments(index).map(arg => arg.name);

    let argCounter = fa.length + 1;
    
    while (takenNames.includes(`argument${argCounter}`)) {
      argCounter++;
    }
  
    fa.push(
      this.createArgumentForm(new Argument(`argument${argCounter}`))
    );
  
    this.cdr.detectChanges();
  }

  createArgumentForm = (arg: Argument): FormGroup => {
    return this.fb.group({
      name: this.fb.control(arg.name, [this.noDuplicateVariables, this.validName, Validators.required]),
      argType: arg.argType,
      isArray: arg.isArray
    });
  }

  getArguments = (index?: number): Argument[] => {
    const argArray = [];
    const fa = this.args(index);

    for (let i = 0; i < fa.length; i++) {
      argArray.push(this.argumentFromForm(fa.at(i) as FormGroup)); 
    }

    return argArray;
  }

  ////// REQUESTS

  requests(index?: number): FormArray {
    return ((this.commandsForm.get('commands') as FormArray).at(index !== undefined ? index : this.tabsIndex) as FormGroup).get('requests') as FormArray;
  }

  requestFromForm(fc: FormGroup): Request {
    const new_request = new Request();

    new_request.url = fc.get('url').value;
    new_request.method = fc.get('method').value;
    new_request.body = (new_request.method === RequestType.POST || new_request.method === RequestType.PUT) ? fc.get('body').value : null;
    new_request.headers = fc.get('headers').value;
    new_request.name = fc.get('name').value;

    return new_request;
  }

  addRequest() {
    const fa = this.requests();
    const takenNames = this.getRequests().map(req => req.name);

    let reqCounter = fa.length + 1;
    
    while (takenNames.includes(`argument${reqCounter}`)) {
      reqCounter++;
    }
  
    fa.push(
      this.createRequestForm(new Request(`request${reqCounter}`))
    );
  
    this.cdr.detectChanges();
  }

  createRequestForm(req: Request): FormGroup {
    return this.fb.group({
      name: this.fb.control(req.name, [Validators.required, this.noDuplicateVariables, this.validName]),
      url: (req.url),
      headers: req.headers,
      body: this.fb.control({value: req.body, disabled: true}),
      method: req.method
    });
  }

  getRequests = (index?: number): Request[] => {
    const argArray = [];
    const fa = this.requests(index);

    for (let i = 0; i < fa.length; i++) {
      argArray.push(this.requestFromForm(fa.at(i) as FormGroup)); 
    }

    return argArray;
  }

  ////// VALIDATORS
  
  validName = (c: FormControl) => {
    return /^[a-zA-Z][_0-9a-zA-Z]*$/.test(c.value) ? null : { 
      validName: {
        valid: false
      }
    };
  }

  noDuplicateVariables = (c: FormControl) => {
    if (!this.args()) {
      return null;
    }

    return this.getArguments()
      .map(argument => argument.name)
      .concat(
        this.getRequests().map(request => request.name)
      )
      .filter(name => name === c.value).length == 1 ? null : { noDuplicates: { valid: false } };
  }

  noDuplicateCommands = (c: FormControl) => {
    if (!this.commands()) {
      return null;
    }

    return this.getCommands().filter(cmd => cmd.name === c.value).length == 1 ? null : {
      noDuplicates: {
        valid: false
      }
    };
  }

  test(x) {
    console.log(this.getCommands())
  }
}
