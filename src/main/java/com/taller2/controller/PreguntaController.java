package com.taller2.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taller2.model.prueba.Opcion;
import com.taller2.model.prueba.Pregunta;
import com.taller2.model.prueba.Prueba;
import com.taller2.model.prueba.Tema;
import com.taller2.service.PruebaServiciosImpl;
import com.taller2.view.prueba.crearpregunta.PreguntaMultipleOpcionDTO;
import com.taller2.view.prueba.crearpregunta.PreguntaVFDTO;

@Controller
public class PreguntaController {
	@Autowired
	private PruebaServiciosImpl pruebaServiciosImpl;

	@GetMapping("/prueba/crearPregunta")
    public String agregarPregunta (Model model) {
 		
 		return "prueba/crearPregunta";
 	}
	@GetMapping("/prueba/crearPregunta/verdaderoFalso")
    public String verdaderoFalsoPrincipal (Model model) {
 		List<Tema> temas = pruebaServiciosImpl.obtenerTemas();
 		PreguntaVFDTO preguntaVF = new PreguntaVFDTO();
 		
		model.addAttribute("temas", temas);
		model.addAttribute("preguntaVFDTO", preguntaVF);
		
 		return "prueba/fragment_preguntaVF:: fragmento";
 	}
 	
	@GetMapping("/prueba/crearPregunta/multipleOpcion")
    public String multipleOpcionPrincipal (Model model) {
 		List<Tema> temas = pruebaServiciosImpl.obtenerTemas();
 		
		model.addAttribute("temas", temas);
 		return "prueba/fragment_preguntaMOpcion:: fragmento";
 	}
	
 	
 	@PostMapping("/prueba/grabarPreguntaVF")
    public String grabarPreguntaVF(@ModelAttribute("preguntaVFDTO") PreguntaVFDTO preguntaVF,
    		RedirectAttributes redirectAttributes) {
 		System.out.println(preguntaVF);
 		
 		//tranformo la información que se obtiene de la capa de presentacion en objetos del modelo
 		Pregunta pregunta = new Pregunta();
 		pregunta.setEnunciado(preguntaVF.getEnunciado());
 		pregunta.setIdOpcionCorrecta(preguntaVF.getOpcionCorrecta());
 		pregunta.setIdTema(preguntaVF.getIdTema());
 		List<Opcion> opciones = new java.util.ArrayList<Opcion>();
 		opciones.add(new Opcion("Verdadero"));
 		opciones.add(new Opcion("Falso"));
 		pregunta.setOpciones(opciones);
 		
 		pruebaServiciosImpl.altaPreguntaConOpciones(pregunta);
 		
 		redirectAttributes.addFlashAttribute("mensajeOk",  "La pregunta se guardo correctamente");
 		return "redirect:/prueba/crearPregunta";
    }
 	
 	
 	@PostMapping("prueba/guardarPreguntaMultipleOpcion")
    public String guardarPregunta(@RequestBody PreguntaMultipleOpcionDTO pregunta, 
    		RedirectAttributes redirectAttributes) {
      
 		System.out.println(pregunta);
        
 		//pruebaServiciosImpl.altaPreguntaConOpciones(new Pregunta());
 		
 		redirectAttributes.addFlashAttribute("mensajeOk",  "La pregunta se guardo correctamente");
 		return "redirect:/prueba/crearPregunta";
    }
 	
}