package com.spring.boot.resturantbackend.controllers.contact_info;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.services.contact_info.ContactInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Contact info Controller", description = "operations on Contacts info")
@RestController
@RequestMapping("/contacts-info")
@CrossOrigin("http://localhost:4200")
public class ContactInfoController {
    @Autowired
    private ContactInfoService contactInfoService;

    @Operation(summary = "create contact info", description = "create contact info in restaurant")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Http Status create contact"), @ApiResponse(responseCode = "500", description = "Http Status internal server error", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),})
    @PreAuthorize("authentication.authorities.size()==1 and hasRole('USER')")
    @PostMapping("/create-contact-info")
    public ResponseEntity<ContactInfoDto> createContactInfo(@Valid @RequestBody ContactInfoDto contactInfoDto) {
        return ResponseEntity.created(URI.create("/all-categories")).body(contactInfoService.createContactInfo(contactInfoDto));
    }

    @Operation(summary = "get all contacts info for admin", description = "all contacts info for admin in restaurant")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Http Status get all contacts"), @ApiResponse(responseCode = "500", description = "Http Status internal server error", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),})
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/contacts-info")
    public ResponseEntity<List<ContactInfoDto>> getAllContactsInfo(@RequestParam(required = false) String filter) {
        return ResponseEntity.ok(contactInfoService.allContactInfos(filter));
    }


}
