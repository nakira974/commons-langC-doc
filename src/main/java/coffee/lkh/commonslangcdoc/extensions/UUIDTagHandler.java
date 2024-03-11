package coffee.lkh.commonslangcdoc.extensions;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.DynamicAttributes;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.util.Random;
import java.util.UUID;

import static jakarta.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED;

/**
 * @author Maxim Loukhal
 * This class is a custom JSP tag handler that handles creating and setting a UUID attribute.
 * It extends the TagSupport class and implements the DynamicAttributes interface.
 * The UUID is created during the PostConstruct phase and attached as a dynamic attribute during the start of the tag.
 */
public class UUIDTagHandler extends TagSupport implements DynamicAttributes {
    /**
     * A globally unique identifier represented as a Java UUID object.
     */
    private UUID uuid;

    /**
     * Initializes the UUID with a randomly created UUID upon construction of the class.
     */
    @PostConstruct
    public void init() {
        this.uuid = UUID.randomUUID();  // get UUID from UUIDProducer
    }

    /**
     * This method starts tag processing and sets the UUID as an attribute on the customBean. The UUID is represented
     * as a string and a random character is prefixed to it for uniqueness in the event of collision.
     * It then calls setDynamicAttribute to set this UUID string as an attribute.
     *
     * @return EVAL_BODY_BUFFERED to control the processing of the body content of the tag.
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doStartTag() throws JspException {
        // Retrieve the UUID from the producer bean
        String uuid = this.uuid.toString();
        uuid = uuid.replace("-", "");
        Random rnd = new Random();
        char randomChar = (char) (rnd.nextInt(26) + 'a');
        uuid = randomChar + uuid;
        // Set the UUID as an attribute on the customBean
        setDynamicAttribute(null, "uuid", uuid);
        // Continue with buffered body evaluation:
        return EVAL_BODY_BUFFERED;
    }

    /**
     * Sets a dynamic attribute in the page context.
     *
     * @param uri       the namespace of the attribute, or null if in the default namespace.
     * @param localName the name of the attribute being set.
     * @param value     the value of the attribute
     * @throws JspException if the tag handler wishes to signal that it does not accept the given attribute. The
     *                      container must not call doStartTag() or doTag() for this tag.
     */
    @Override
    public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
        pageContext.setAttribute(localName, value);
    }

    // Other methods and logic for your custom tag handler
}