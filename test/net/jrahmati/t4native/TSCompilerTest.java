package net.jrahmati.t4native;

import java.util.ArrayList;
import net.jrahmati.t4native.compilers.TSToJavaCompiler;
import net.jrahmati.t4native.compilers.TSToSwiftCompiler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jafar
 */
public class TSCompilerTest {

    private final TSToJavaCompiler tSToJavaCompiler = new TSToJavaCompiler();
    private final TSToSwiftCompiler tSToSwiftCompiler = new TSToSwiftCompiler();

    public TSCompilerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of analyzeLine method of class TSCompiler using below comment 
     * tagged by testAnalyzeLineWithSimpleHelloWorldClass
     * 
     * testAnalyzeLineWithSimpleHelloWorldClass:
     * //A simple hello world class
     * class HelloWorld extends HelloWorldBase implements IGreeter, ISerializable {
     * 
     *  constructor(myArg:myType) {
     *      this.myArg = myArg;
     *  }
     * 
     *  public MyMethod(myArg:myType) : returnType {
     *      print("Hello");
     *      var decimal:Number = 0;
     *      while(isTrue == true) {
     *          customGreeting += " " + decimal;
     *          decimal += 1;
     *      }
     *      human.talk();
     *  }
     * 
     * }
     * :end
     * 
     * testAnalyzeLineWithSimpleHelloWorldClass.java:
     * //A simple hello world class
     * public class HelloWorld extends HelloWorldBase implements IGreeter, ISerializable {
     * 
     *  HelloWorld(myType myArg) {
     *      this.myArg = myArg;
     *  }
     * 
     *  public returnType MyMethod(myType myArg) {
     *      print("Hello");
     *      Number decimal = 0;
     *      while(isTrue == true) {
     *          customGreeting += " " + decimal;
     *          decimal += 1;
     *      }
     *      human.talk();
     *  }
     * 
     * }
     * :end
     * 
     * testAnalyzeLineWithSimpleHelloWorldClass.swift:
     * //A simple hello world class
     * public class HelloWorld: HelloWorldBase, IGreeter, ISerializable {
     * 
     *  init(_ myArg: myType) {
     *      self.myArg = myArg
     *  }
     * 
     *  public func MyMethod(_ myArg: myType) -> returnType {
     *      print("Hello")
     *      var decimal:Number = 0
     *      while(isTrue == true) {
     *          customGreeting += " " + decimal
     *          decimal += 1
     *      }
     *      human.talk()
     *  }
     * 
     * }
     * :end
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testCompiler() throws Exception {
        ArrayList<String> myTsLines = 
                StringHelper.getCommentLinesFromClassByTag(getClass(), "testAnalyzeLineWithSimpleHelloWorldClass");
        ArrayList<String> mySwiftLines = 
                StringHelper.getCommentLinesFromClassByTag(getClass(), "testAnalyzeLineWithSimpleHelloWorldClass.swift");
        ArrayList<String> myJavaLines = 
                StringHelper.getCommentLinesFromClassByTag(getClass(), "testAnalyzeLineWithSimpleHelloWorldClass.java");
        
        int line = 1;
        for (String myTsLine : myTsLines) {
            String genSwiftLine = tSToSwiftCompiler.CompileLine(myTsLine, "HelloWorld.ts", line);
            String genJavaLine = tSToJavaCompiler.CompileLine(myTsLine, "HelloWorld.ts", line);
            String expectedSwiftLine = mySwiftLines.get(line-1);
            String expectedJavaLine = myJavaLines.get(line-1);
            Assert.assertEquals(expectedSwiftLine.trim(), genSwiftLine.trim());
            Assert.assertEquals(expectedJavaLine.trim(), genJavaLine.trim());
            line++;
        }
    }

    /**
     * 
     * testAnalyzeLineWithSimpleInterface:
     * interface IGreeter extends ISerializable {
     *  MyMethod(myArg: myType) : returnType;
     *  GetGreetingByTag(tag: String) : String;
     * }
     * :end
     * 
     * testAnalyzeLineWithSimpleInterface.swift:
     * public protocol IGreeter: ISerializable {
     *  func MyMethod(_ myArg: myType) -> returnType
     *  func GetGreetingByTag(_ tag: String) -> String
     * }
     * :end
     * 
     * testAnalyzeLineWithSimpleInterface.java:
     * public interface IGreeter extends ISerializable {
     *  returnType MyMethod(myType myArg);
     *  String GetGreetingByTag(String tag);
     * }
     * :end
     * @throws Exception 
     */
    @Test
    public void testAnalyzeLineWithSimpleInterface() throws Exception {
        ArrayList<String> myTsLines = 
                StringHelper.getCommentLinesFromClassByTag(getClass(), "testAnalyzeLineWithSimpleInterface");
        ArrayList<String> mySwiftLines = 
                StringHelper.getCommentLinesFromClassByTag(getClass(), "testAnalyzeLineWithSimpleInterface.swift");
        ArrayList<String> myJavaLines = 
                StringHelper.getCommentLinesFromClassByTag(getClass(), "testAnalyzeLineWithSimpleInterface.java");
        
        int line = 1;
        for (String myTsLine : myTsLines) {
            String genSwiftLine = tSToSwiftCompiler.CompileLine(myTsLine, "IGreeter.ts", line);
            String genJavaLine = tSToJavaCompiler.CompileLine(myTsLine, "IGreeter.ts", line);
            String expectedSwiftLine = mySwiftLines.get(line-1);
            String expectedJavaLine = myJavaLines.get(line-1);
            Assert.assertEquals(expectedSwiftLine.trim(), genSwiftLine.trim());
            Assert.assertEquals(expectedJavaLine.trim(), genJavaLine.trim());
            line++;
        }
    }
}
