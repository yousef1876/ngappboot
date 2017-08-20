package com.development.backend.ng.springmvc.api.user;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
/*import org.springframework.oxm.xstream.XStreamMarshaller;*/
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.development.backend.ng.springmvc.WebMvcConfig;
import com.development.backend.ng.springmvc.bean.RegisterBean;
import com.development.backend.ng.springmvc.bean.SecretCodeBean;
import com.development.backend.ng.springmvc.bean.XmlGate;
import com.google.common.collect.ImmutableList;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory
            .getLogger(UserController.class);
    private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
   
    
    @Autowired
    private RestTemplate restTemplate; 
    
     @PostMapping(value = "/api/protei/secret" )
     public ResponseEntity<XmlGate> secret(@RequestBody SecretCodeBean body)
     {
     	String postData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
     			+"\n"+"<xml_gate><auth><login>admin@admin.com</login><password>sql</password><one_time>true</one_time></auth><command>GET_VERIFICATION_CODE</command><arguments><login>"+body.getMobile()+"</login></arguments></xml_gate>";
         
         RestTemplate restTemplate =  new RestTemplate();
         //Create a list for the message converters
         List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
         List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
         for (HttpMessageConverter<?> converter : converters) {
            /* if (converter instanceof MarshallingHttpMessageConverter) {
            	 
            	 XStreamMarshaller marshaller = new XStreamMarshaller();
         	    MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(marshaller);
         	    marshallingConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("text", "xml", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
             }*/
             
             if (converter instanceof StringHttpMessageConverter) {
            	 
                	StringHttpMessageConverter marshallingConverter = new StringHttpMessageConverter();
         	        marshallingConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("text", "xml", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
             }
         }
         
         
         //Add the message converters to the restTemplate
         restTemplate.setMessageConverters(converters);


         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(org.springframework.http.MediaType.TEXT_XML);
         HttpEntity<String> request = new HttpEntity<String>(postData, headers);
    
         String response = restTemplate.postForEntity("URL", request, String.class).getBody();
      
         List<String> arr = toknize(response , "\n\t");
         
         String str = null;
         
         for(String s : arr){
        	 
        	 str += " "+ s;
         }
         
         /*SecretCodeModel model = new SecretCodeModel();
 		 model.setValue(str);
 		 model.setId(System.currentTimeMillis());
 		 model.setMobileNumber(body.getMobile());
 		 model.setValue(body.getMobile());
 		 model.setResponse(str);
 		 secCodeRepository.save(model);*/
 		
 		 
 		
 		 
 		 
         XmlGate gate = new XmlGate();
         if(!arr.isEmpty()){
        	 gate.setStatus(arr.get(2));
			 gate.setCode(arr.get(3));
			 gate.setDescription(arr.get(4));
			 return new ResponseEntity<>(gate,HttpStatus.OK);
        	 /*String first = arr.get(0);
        	 String second = arr.get(1);
        	 
        	 if(!second.equalsIgnoreCase("")&&second !=null){
        		 List<String> list = toknize(second , "\n\t");
        		 
        		 if(!list.isEmpty()){
        			 gate.setStatus(list.get(0));
        			 gate.setCode(list.get(1));
        			 gate.setDescription(list.get(2));
        			 return new ResponseEntity<>(gate,HttpStatus.OK);
        		 } 		 
        	 }*/
       
         }
         
         return new ResponseEntity<>(gate,HttpStatus.OK);
     }
     private List<String> toknize(String response , String split){
    	 
    	 StringTokenizer tok = new StringTokenizer(response,split);
         String first = null;
         String second = null;
         List<String> strs = new ArrayList<>();
         
         while(tok.hasMoreTokens()){
         	 
         strs.add(tok.nextToken()); 	 
        	 
         }
         return strs;
     }
     @PostMapping(value = "/api/protei/registration" )
     public ResponseEntity<XmlGate> registration(@RequestBody RegisterBean body) throws InstantiationException, IllegalAccessException, JAXBException, XMLStreamException
     {
     	String postData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
     			+"\n"+"<xml_gate><auth><login>admin@admin.com</login><password>sql</password><one_time>true</one_time></auth><command>REGISTER</command><arguments><login>"+body.getMobile()+"</login><verification_code>"+body.getCode()+"</verification_code></arguments></xml_gate>";
         RestTemplate rt = new RestTemplate();
         RestTemplate restTemplate =  new RestTemplate();
         //Create a list for the message converters
         List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
         //Add the String Message converter
         messageConverters.add(new StringHttpMessageConverter());
         messageConverters.add(new Jaxb2RootElementHttpMessageConverter()); 
         messageConverters.add(new WebMvcConfig().mappingJackson2HttpMessageConverter());
         //Add the message converters to the restTemplate
         restTemplate.setMessageConverters(messageConverters);


         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(org.springframework.http.MediaType.TEXT_XML);;
         HttpEntity<String> request = new HttpEntity<String>(postData, headers);
    
         String response = restTemplate.postForEntity("URL", request, String.class).getBody();
         /*XmlGate bean = parseXml(response,XmlGate.class.newInstance());*/
         List<String> arr = toknize(response , "\n\t");
         XmlGate gate = new XmlGate();
         String sr = null;
         for(String s : arr){
        	 sr +=" "+s; 
         }
         
         /*RegistrationModel model = new RegistrationModel();
         
         model.setId(System.currentTimeMillis());
         model.setMobileNumber(body.getMobile());
         model.setValue(body.getCode());
         model.setResponse(sr);
         registrationRepository.save(model);*/
  		 
         
         if(!arr.isEmpty()){
        	 gate.setStatus(arr.get(2));
			 gate.setCode(arr.get(3));
			 gate.setDescription(arr.get(4));
			 return new ResponseEntity<>(gate,HttpStatus.OK);
        	 /*String first = arr.get(0);
        	 String second = arr.get(1);
        	 
        	 if(!second.equalsIgnoreCase("")&&second !=null){
        		 List<String> list = toknize(second , "\n\t");
        		 
        		 if(!list.isEmpty()){
        			 gate.setStatus(list.get(0));
        			 gate.setCode(list.get(1));
        			 gate.setDescription(list.get(2));
        			 return new ResponseEntity<>(gate,HttpStatus.OK);
        		 } 		 
        	 }*/
       
         }
         
         
         
         return new ResponseEntity<>(gate,HttpStatus.OK);
     }
     
    
    
}
