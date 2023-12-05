package ar.edu.uade.tpoapi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.uade.tpoapi.services.TemplateService;

@RestController
@RequestMapping("/Template")
public class ControladorTemplate {
    
    @Autowired
    private TemplateService templateService;

    @PostMapping("/Save")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleado') or hasRole('SuperAdmin')")
    public ResponseEntity<?> saveNewTemplate(@RequestParam("file") MultipartFile templateFile ,@RequestHeader("title") String title , @RequestHeader("description") String description , @RequestHeader("vars") List<String> vars){
		return templateService.saveNewTemplate(templateFile, title, description, vars);
	}
}
