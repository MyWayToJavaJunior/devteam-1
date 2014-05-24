package by.bsu.mmf.devteam.logic.bean.verifiable.builder;

import by.bsu.mmf.devteam.logic.bean.verifiable.ProposedOrder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * This builder builds proposed order
 * @author Dmitry Petrovich
 * @since 2.2.0-beta
 */
public class VerifiableBuilder {
    private static final String SPECIFICATION_NAME = "nameOfNewSpec";

    public static ProposedOrder build(HttpServletRequest request) {
        ProposedOrder order = new ProposedOrder();
        try {
            order.setSpecification(new String(request.getParameter(SPECIFICATION_NAME).getBytes("UTF-8"), "CP1251"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return order;
    }

}
