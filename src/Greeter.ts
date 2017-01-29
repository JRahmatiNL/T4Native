class Greeter implements IGreeter {

    greeting: string = "interface's property";
    userToGreet: string = "test";

    constructor(message: string) {
        this.greeting = message + this.userToGreet;
    }

    greetUsingExtraName(userToGreet: string): string {
        this.userToGreet = userToGreet;
        return this.greet();
    }

    greet(): string {
        var customGreeting: String = new String();
        customGreeting = this.greeting;
        if (this.userToGreet != "") {
            customGreeting += ", " + this.greeting;
        }
        return customGreeting + "! Welcome to the cross platform typescript greeter!";
    }
}
