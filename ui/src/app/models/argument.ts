export enum ArgumentType {
  STRING,
  NUMBER,
  BOOLEAN,
  CHANNEL_MEMBER,
  JSON_OBJECT
}

export class Argument {
  name: string;
  isArray: boolean;
  argType: ArgumentType;

  constructor(name: string) {
    this.name = name;
    this.isArray = false;
    this.argType = ArgumentType.STRING;
  }

  isarray(isArray: boolean): Argument {
    this.isArray = isArray;
    return this;
  }

  argtype(argType: ArgumentType): Argument {
    this.argType = argType;
    return this;
  }

  argTypeToString(argType: ArgumentType) {
    if (argType === ArgumentType.STRING) {
      return "String";
    } else if (argType === ArgumentType.NUMBER) {
      return "String";
    } else if (argType === ArgumentType.BOOLEAN) {
      return "String";
    } else if (argType === ArgumentType.CHANNEL_MEMBER) {
      return "String";
    } else if (argType === ArgumentType.JSON_OBJECT) {
      return "String";
    }

    return "Null";
  }
}