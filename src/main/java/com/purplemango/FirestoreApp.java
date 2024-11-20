package com.purplemango;

import com.google.cloud.firestore.Firestore;
import com.purplemango.handler.PermissionHandler;
import com.purplemango.handler.RoleHandler;
import com.purplemango.model.Permission;
import com.purplemango.model.Role;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = "com.purplemango")
@OpenAPIDefinition(info = @Info(title = "Firestore App", version = "1.0", description = "API documentation for Firestore app"))
public class FirestoreApp {

    @Autowired
    private Firestore firestore;

    public static void main(String[] args) {
        run(FirestoreApp.class, args);
    }

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/api/v1/permissions/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, beanClass = PermissionHandler.class, beanMethod = "update",
                            operation = @Operation(operationId = "updatePermission", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Permission.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid permission ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Permission not found")}, parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "employeeId")}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Permission.class)))
                            )
                    ),
                    @RouterOperation(path = "/api/v1/permissions", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = PermissionHandler.class, beanMethod = "create",
                            operation = @Operation(operationId = "addPermission", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PermissionHandler.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Permission info supplied")}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Permission.class)))
                            )
                    ),
                    @RouterOperation(path = "/api/v1/permissions/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = Permission.class, beanMethod = "delete"
                            , operation = @Operation(operationId = "deletePermission", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                            @ApiResponse(responseCode = "400", description = "Invalid permission ID supplied"),
                            @ApiResponse(responseCode = "404", description = "Permission not found")}, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "id")})
                    ),
                    @RouterOperation(path = "/api/v1/permissions", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = PermissionHandler.class, beanMethod = "all"
                            , operation = @Operation(operationId = "getAllPermission", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                            @ApiResponse(responseCode = "204", description = "No content"),
                            @ApiResponse(responseCode = "404", description = "Permission not found")}
                    )),
                    @RouterOperation(path = "/api/v1/permissions?name={permission-name}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = PermissionHandler.class, beanMethod = "findByName"
                            , operation = @Operation(operationId = "findPermissionByName", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                            @ApiResponse(responseCode = "400", description = "Invalid permission ID supplied"),
                            @ApiResponse(responseCode = "404", description = "Permission not found")}, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "permissionName")}
                    )),
                    @RouterOperation(path = "/api/v1/permissions/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, beanClass = PermissionHandler.class, method = RequestMethod.GET, beanMethod = "findById",
                            operation = @Operation(operationId = "findPermissionById", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Permission.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid permission details supplied")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id")}
                            ))

            })
    public RouterFunction<ServerResponse> permissionRoutes(PermissionHandler requestHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/api/v1/permissions"),
                        requestHandler::create)
                .andRoute(RequestPredicates.GET("/api/v1/permissions"),
                        requestHandler::all)
                .andRoute(RequestPredicates.GET("/api/v1/permissions/{id}"),
                        requestHandler::findById)
                .andRoute(RequestPredicates.GET("/api/v1/permissions?name={name}"),
                        requestHandler::findByName)
                .andRoute(RequestPredicates.PUT("/api/v1/permissions/{id}"),
                        requestHandler::update)
                .andRoute(RequestPredicates.DELETE("/api/v1/permissions/{id}"),
                        requestHandler::delete);
    }

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/api/v1/roles/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, beanClass = RoleHandler.class, beanMethod = "update",
                            operation = @Operation(operationId = "updateRole", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Role.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid role ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Role not found")}, parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "employeeId")}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Role.class)))
                            )
                    ),
                    @RouterOperation(path = "/api/v1/roles", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = RoleHandler.class, beanMethod = "create",
                            operation = @Operation(operationId = "addRole", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Role.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid role supplied")}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Role.class)))
                            )
                    ),
                    @RouterOperation(path = "/api/v1/roles/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = RoleHandler.class, beanMethod = "delete"
                            , operation = @Operation(operationId = "deleteRole", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                            @ApiResponse(responseCode = "400", description = "Invalid role ID supplied"),
                            @ApiResponse(responseCode = "404", description = "Role not found")}, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "id")})
                    ),
                    @RouterOperation(path = "/api/v1/roles", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = RoleHandler.class, beanMethod = "all"
                            , operation = @Operation(operationId = "getAllRole", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                            @ApiResponse(responseCode = "204", description = "No content"),
                            @ApiResponse(responseCode = "404", description = "Role not found")}
                    )),
                    @RouterOperation(path = "/api/v1/roles?name={role-name}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = RoleHandler.class, beanMethod = "findByName"
                            , operation = @Operation(operationId = "findRoleByName", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                            @ApiResponse(responseCode = "400", description = "Invalid role ID supplied"),
                            @ApiResponse(responseCode = "404", description = "Role not found")}, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "serverName")}
                    )),
                    @RouterOperation(path = "/api/v1/roles/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, beanClass = RoleHandler.class, method = RequestMethod.GET, beanMethod = "findById",
                            operation = @Operation(operationId = "findRoleById", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Role.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid role details supplied")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id")}
                            ))

            })
    public RouterFunction<ServerResponse> roleRoutes(RoleHandler requestHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/api/v1/roles"),
                        requestHandler::create)
                .andRoute(RequestPredicates.GET("/api/v1/roles"),
                        requestHandler::all)
                .andRoute(RequestPredicates.GET("/api/v1/roles/{id}"),
                        requestHandler::findById)
                .andRoute(RequestPredicates.GET("/api/v1/roles?name={user-name}"),
                        requestHandler::findByName)
                .andRoute(RequestPredicates.PUT("/api/v1/roles/{id}"),
                        requestHandler::update)
                .andRoute(RequestPredicates.DELETE("/api/v1/roles/{id}"),
                        requestHandler::delete);
    }
}
