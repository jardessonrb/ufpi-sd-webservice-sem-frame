package sd.ufpi.application.controllers;

import sd.ufpi.application.domain.dto.MensagemDTO;
import sd.ufpi.application.domain.dto.UsuarioDTO;
import sd.ufpi.application.domain.form.MensagemForm;
import sd.ufpi.application.domain.form.MensagemReencaminhadaForm;
import sd.ufpi.core.rest.anotations.DeleteMapping;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PatchMapping;
import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.PostMapping;
import sd.ufpi.core.rest.anotations.PutMapping;
import sd.ufpi.core.rest.anotations.QueryParam;
import sd.ufpi.core.rest.anotations.RequestBody;
import sd.ufpi.core.rest.anotations.RequestMapping;
import sd.ufpi.core.rest.anotations.RequestMethod;
import sd.ufpi.core.rest.types.ResponseEntity;

@RequestMapping(path = "/message")
public class MessageController {

}
