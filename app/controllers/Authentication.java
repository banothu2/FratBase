package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Authentication extends Controller {

    public static Result register() {
        return ok(register.render("Please Register at Frat-Base"));
    }


}
