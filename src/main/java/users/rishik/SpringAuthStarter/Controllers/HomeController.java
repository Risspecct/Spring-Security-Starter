package users.rishik.SpringAuthStarter.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class HomeController {

    @Operation(summary = "API Root", description = "Simple root message guiding users to Swagger UI")
    @ApiResponse(responseCode = "200", description = "Instruction returned")
    @GetMapping("/")
    public String home(){
        return "Use /docs to access the swagger ui from the browser";
    }

    @Operation(summary = "Redirect to Swagger UI", description = "Redirects /docs to the Swagger UI page")
    @ApiResponse(responseCode = "302", description = "Redirected to Swagger UI")
    @GetMapping("/docs")
    public RedirectView swaggerUi(){
        return new RedirectView("/swagger-ui/index.html");
    }
}
