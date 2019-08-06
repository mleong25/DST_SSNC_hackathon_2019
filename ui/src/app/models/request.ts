export enum RequestType {
  GET,
  POST,
  PUT,
  DELETE
}

export class Request {
  method: RequestType;
  url: string;
  headers: {[key: string]: string};
  body: string;
  name: string;

  constructor(name?: string) {
    this.name = name || '';
    this.method = RequestType.GET;
    this.headers = {};
    this.body = '';
    this.url = '';
  }
}