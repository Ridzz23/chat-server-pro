import static org.junit.Assert.*;
import org.junit.*;
import java.net.URI;

public class SemanticAnalysisHandlerTests {
  @Test
  public void handleRequest1() throws Exception {
    ChatHandler h = new ChatHandler();
    String url = "http://localhost:4000/chat?user=joe&message=hi";
    URI input = new URI(url);
    String expected = "joe: hi\n\n";
    assertEquals(expected, h.handleRequest(input));
  }

  @Test
  public void handleRequestMulti() throws Exception {
    ChatHandler h = new ChatHandler();
    String url1 = "http://localhost:4000/chat?user=onat&message=good%20luck";
    String url2 = "http://localhost:4000/chat?user=edwin&message=with%20your%20demo!";
    URI input1 = new URI(url1);
    URI input2 = new URI(url2);
    String expected = "onat: good luck\n\nedwin: with your demo!\n\n";
    h.handleRequest(input1);
    assertEquals(expected, h.handleRequest(input2));
  }

  @Test
  public void handleRequestSemanticAnalysis() throws Exception {
    ChatHandler h = new ChatHandler();
    String url1 = "http://localhost:4000/chat?user=onat&message=😂";
    String url2 = "http://localhost:4000/chat?user=onat&message=doggy🥹!!!";
    String url3 = "http://localhost:4000/chat?user=onat&message=TGIThanksgiving";
    String url4 = "http://localhost:4000/semantic-analysis?user=onat";

    URI input1 = new URI(url1);
    URI input2 = new URI(url2);
    URI input3 = new URI(url3);
    URI input4 = new URI(url4);
    String expected = "onat: 😂 This message has a LOL vibe.\n\nonat: doggy🥹!!! This message has a awwww vibe. This message ends forcefully.\n\nonat: TGIThanksgiving\n\n";

    h.handleRequest(input1);
    h.handleRequest(input2);
    h.handleRequest(input3);
    assertEquals(expected, h.handleRequest(input4));
  
  }

    @Test
    public void handleRequestSemanticAnalysisUserNew() throws Exception {
    ChatHandler h = new ChatHandler();
    String url1 = "http://localhost:4000/chat?user=onat&message=😂";
    String url2 = "http://localhost:4000/chat?user=edwin&message=doggy🥹!!!";
    String url3 = "http://localhost:4000/chat?user=onat&message=TGIThanksgiving";
    String url4 = "http://localhost:4000/semantic-analysis?user=onat";

    URI input1 = new URI(url1);
    URI input2 = new URI(url2);
    URI input3 = new URI(url3);
    URI input4 = new URI(url4);
    String expected = "onat: 😂 This message has a LOL vibe.\n\nedwin: doggy🥹!!! This message has a awwww vibe. This message ends forcefully.\n\nonat: TGIThanksgiving\n\n";

    h.handleRequest(input1);
    h.handleRequest(input2);
    h.handleRequest(input3);
    assertEquals(expected, h.handleRequest(input4));
  }


    @Test
    public void handleRequestSemanticAnalysisManyEmojis() throws Exception {
    ChatHandler h = new ChatHandler();
    String url1 = "http://localhost:4000/chat?user=onat&message=🥹😂";

    URI input1 = new URI(url1);
    String expected = "onat: 🥹! 😂 This message has a awwww vibe.This message has a LOL vibe.";

    h.handleRequest(input1);
    assertEquals(expected, h.handleRequest(input1));
  }
}
