package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.domain.ContentTypes;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!isPossibleAccessUserList(httpRequest.getPath())) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }

        try {
            byte[] body = makeUserListBody(httpRequest.getPath());
            httpResponse.addHeader(CONTENT_TYPE, ContentTypes.HTML.getType());
            httpResponse.response200Header(body.length);
            httpResponse.responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.response404();
        }

    }

    private boolean isPossibleAccessUserList(String path) {
        try {
            return login && FileIoUtils.isExistFile("./templates" + path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] makeUserListBody(String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);
        List<User> users = new ArrayList<>(DataBase.findAll());
        return template.apply(users).getBytes(StandardCharsets.UTF_8);
    }

}
