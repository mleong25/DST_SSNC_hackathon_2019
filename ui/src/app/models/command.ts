import { Argument } from './argument';
import { Request } from './request';

export class Command {
  name: string;
  desc: string;
  args: Argument[];
  requests: Request[];
  response: string;
  
  constructor(name: string, args?: Argument[], requests?: Request[]) {
    this.name = name;
    this.desc = "";
    this.args = args || [];
    this.requests = requests || [];
    this.response = `send("Hello world!");`;
  }
}