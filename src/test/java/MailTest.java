import com.github.newkayak12.config.SimpleEmailSender;
import com.github.newkayak12.enums.Vendor;
import com.github.newkayak12.message.HtmlMailForm;
import com.github.newkayak12.message.SimpleMailForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class MailTest {
    private SimpleEmailSender simpleMailMessage;

    @BeforeEach
    public void setUp () {
        this.simpleMailMessage = new SimpleEmailSender(Vendor.GMAIL, "/email/", null, null);
    }

    @DisplayName("메일 보내기 테스트")
    @Nested
    public class SendTest {

        @Test
        public void simpleMail() {
            simpleMailMessage.send(SimpleMailForm.write("subject", "message", "newkayak12@gmail.com"));
        }

        @Test
        public void htmlMail() {
            simpleMailMessage.send(
                    HtmlMailForm.write("subject", "sample", new HashMap<String,Object>(){{
                        put("title", "TITLE");
                        put("text", "TEXT???");
                    }}, "newkayak12@gmail.com")
            );
        }

    }
}
