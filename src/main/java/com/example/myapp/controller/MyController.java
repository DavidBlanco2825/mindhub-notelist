package com.example.myapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "MyController", description = "Operations related to greetings, submissions, and user management")
public class MyController {

    @GetMapping("/greeting")
    @Operation(summary = "Get Greeting", description = "Returns a greeting message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Greeting message successfully returned.",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Hello, World!")))
    })
    public String getGreeting() {
        return "Hello, World!";
    }

    @PostMapping("/submit")
    @Operation(summary = "Submit Data", description = "Receives data and returns a confirmation message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data successfully received.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Data received: Sample data"))),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid data format.")))
    })
    public String submitData(@RequestBody String data) {
        return "Data received: " + data;
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get User by ID", description = "Returns user information based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ID successfully returned.",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "User ID: 1"))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public String getUserById(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be retrieved", required = true, example = "1") Long id) {
        return "User ID: " + id;
    }

    @GetMapping("/search")
    @Operation(summary = "Search", description = "Searches for items based on a query parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results successfully returned.",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Search results for: sample query"))),
            @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid query parameter.",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Invalid search query.")))
    })
    public String search(
            @RequestParam(name = "query", defaultValue = "")
            @Parameter(description = "Search query", example = "sample query") String query) {
        return "Search results for: " + query;
    }
}
