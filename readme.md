Prototype Java library for compiling a portion of typescript into swift and Java for use in native development.

"public class HelloWorld extends HelloWorldBase implements IGreeter, ISerializable {" in:
 - java: "public class HelloWorld extends HelloWorldBase implements IGreeter, ISerializable {"
 - swift: "public class HelloWorld extends HelloWorldBase: IGreeter, ISerializable {"
"constructor(myArg:myType) {" in:
  - java: "public HelloWorld(myType myArg)"
  - swift: "init(myArg:myType)"

"this.myArg = myArg;" in:
  - java: "this.myArg = myArg;"
  - swift: "self.myArg = myArg"

NOTE: 
This project is not meant for existing typescript codes, since it has very limited support for the different typescript syntaxes. For example:
  - all braces are expected to be at the same line as the method/interface/class declarations
  - for statements are not supported
  - if and while statements although supported will fail once combined with parentheses like method calls or when comparition operators are omitted:
  <br />
  a) while(methodCall()) // will fail<br />
  b) while(variable) // will fail since there is no comparision operator (e.g. ==,=<,=>, etc.)<br />
  c) while(variable < 10) // will pass!<br />

For more info see test/TSCompilerTest.java