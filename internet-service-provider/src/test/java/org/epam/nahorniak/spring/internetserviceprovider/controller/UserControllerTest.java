package org.epam.nahorniak.spring.internetserviceprovider.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.RequestNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.SuchUserAlreadyExistException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.RequestStatus;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.Role;
import org.epam.nahorniak.spring.internetserviceprovider.service.RequestService;
import org.epam.nahorniak.spring.internetserviceprovider.service.UserService;
import org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestRequestDataUtil;
import org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestRequestDataUtil.REQUEST_NOT_FOUND;
import static org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestUserDataUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private RequestService requestService;

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @Test
    void getAllUsersTest() throws Exception {

        UserDto expectedUser = TestUserDataUtil.createUserDto();
        when(userService.listUsers(PAGE,SIZE)).thenReturn(Collections.singletonList(expectedUser));

        mockMvc.perform(get("/api/v1/user")
                .param("page", String.valueOf(PAGE))
                .param("size", String.valueOf(SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$[0].email").value(expectedUser.getEmail()));
    }

    @Test
    void getUserTest() throws Exception {

        UserDto expectedUser = TestUserDataUtil.createUserDto();
        final String email = expectedUser.getEmail();
        when(userService.getUser(email)).thenReturn(expectedUser);

        mockMvc.perform(get("/api/v1/user/" + email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value(Role.ADMIN.name()));
    }

    @Test
    void createValidUserTest() throws Exception {

        UserDto createBody = TestUserDataUtil.createUserDto();
        UserDto expectedUser = TestUserDataUtil.createUserDto();
        when(userService.createUser(createBody)).thenReturn(expectedUser);

        mockMvc.perform(
                post("/api/v1/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()));
    }

    @Test
    void createInvalidUserTest() throws Exception {

        UserDto createBody = TestUserDataUtil.createUserDto();
        createBody.setEmail("not valid email");
        createBody.setPhone("not valid phone");
        when(userService.createUser(createBody)).thenReturn(createBody);

        mockMvc.perform(
                        post("/api/v1/user/")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void updateUserWithValidBodyTest() throws Exception {

        UserDto updateBody = TestUserDataUtil.createUpdatedUserDto();
        UserDto expectedUser = TestUserDataUtil.createUpdatedUserDto();
        when(userService.updateUser(MOCK_EMAIL,updateBody)).thenReturn(expectedUser);

        System.out.println(expectedUser);

        mockMvc.perform(
                        patch("/api/v1/user/" + MOCK_EMAIL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(MOCK_UPDATE_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(MOCK_UPDATE_LAST_NAME))
                .andExpect(jsonPath("$.status").value(MOCK_UPDATE_STATUS.name()))
                .andExpect(jsonPath("$.country").value(MOCK_UPDATE_COUNTRY))
                .andExpect(jsonPath("$.city").value(MOCK_UPDATE_CITY))
                .andExpect(jsonPath("$.street").value(MOCK_UPDATE_STREET));
    }

    @Test
    void updateUserWithInvalidBodyTest() throws Exception {

        UserDto updateBody = TestUserDataUtil.createUpdatedUserDto();
        updateBody.setFirstName("");
        updateBody.setLastName("");
        UserDto expectedUser = TestUserDataUtil.createUpdatedUserDto();
        when(userService.updateUser(MOCK_EMAIL,updateBody)).thenReturn(expectedUser);

        System.out.println(expectedUser);

        mockMvc.perform(
                        patch("/api/v1/user/" + MOCK_EMAIL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void deleteUserTest() throws Exception {

        doNothing().when(userService).deleteUser(MOCK_EMAIL);

        mockMvc
                .perform(delete("/api/v1/user/" + MOCK_EMAIL))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllRequestsByUserTest() throws Exception {

        RequestDto expectedRequest = TestRequestDataUtil.createRequestDto();
        when(requestService.getAllByUserEmail(MOCK_EMAIL,PAGE,SIZE))
                .thenReturn(Collections.singletonList(expectedRequest));

        System.out.println(expectedRequest);
        mockMvc.perform(
                get("/api/v1/user/"+ MOCK_EMAIL +"/requests")
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TestRequestDataUtil.MOCK_ID))
                .andExpect(jsonPath("$[0].status").value(TestRequestDataUtil.MOCK_STATUS.name()))
                .andExpect(jsonPath("$[0].startDate").value(TestRequestDataUtil.MOCK_START_DATE.toString()))
                .andExpect(jsonPath("$[0].endDate").value(TestRequestDataUtil.MOCK_END_DATE.toString()));
    }

    @Test
    void getCurrentRequestByUserTest() throws Exception {

        RequestDto expectedRequest = TestRequestDataUtil.createRequestDto();
        when(requestService.getActiveOrSuspendedRequestByUserEmail(MOCK_EMAIL)).thenReturn(expectedRequest);

        mockMvc.perform(
                get("/api/v1/user/" + MOCK_EMAIL + "/requests/current"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TestRequestDataUtil.MOCK_ID))
                .andExpect(jsonPath("$.status").value(TestRequestDataUtil.MOCK_STATUS.name()))
                .andExpect(jsonPath("$.startDate").value(TestRequestDataUtil.MOCK_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(TestRequestDataUtil.MOCK_END_DATE.toString()));
    }

    @Test
    void closeCurrentRequestByUserTest() throws Exception {

        RequestDto expectedRequest = TestRequestDataUtil.createRequestDto();
        expectedRequest.setStatus(RequestStatus.CLOSED);
        when(requestService.closeRequest(MOCK_EMAIL)).thenReturn(expectedRequest);

        mockMvc.perform(
                put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/close"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(RequestStatus.CLOSED.name()));
    }

    @Test
    void activateCurrentRequestByUserTest() throws Exception {

        RequestDto expectedRequest = TestRequestDataUtil.createRequestDto();
        expectedRequest.setStatus(RequestStatus.ACTIVE);
        when(requestService.activateRequest(MOCK_EMAIL)).thenReturn(expectedRequest);

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/activate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(RequestStatus.ACTIVE.name()));
    }

    @Test
    void suspendCurrentRequestByUserTest() throws Exception {

        RequestDto expectedRequest = TestRequestDataUtil.createRequestDto();
        expectedRequest.setStatus(RequestStatus.SUSPENDED);
        when(requestService.suspendRequest(MOCK_EMAIL)).thenReturn(expectedRequest);

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/suspend"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(RequestStatus.SUSPENDED.name()));
    }

    @Test
    void addRequestTest() throws Exception {

        RequestDto expectedRequest = TestRequestDataUtil.createRequestDto();
        when(requestService.createRequest(MOCK_EMAIL,MOCK_ID)).thenReturn(expectedRequest);

        mockMvc.perform(
                post("/api/v1/user/"+ MOCK_EMAIL + "/requests/addRequest/"+ MOCK_ID))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TestRequestDataUtil.MOCK_ID))
                .andExpect(jsonPath("$.status").value(TestRequestDataUtil.MOCK_STATUS.name()))
                .andExpect(jsonPath("$.startDate").value(TestRequestDataUtil.MOCK_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(TestRequestDataUtil.MOCK_END_DATE.toString()));
    }

    // Exceptions
    @Test
    void getUserNotFoundTest() throws Exception {

        when(userService.getUser(MOCK_EMAIL)).thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/api/v1/user/" + MOCK_EMAIL))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void createUser_UserWithSuchEmailAlreadyExistsTest() throws Exception{

        UserDto createBody = TestUserDataUtil.createUserDto();
        when(userService.createUser(createBody))
                .thenThrow(new SuchUserAlreadyExistException(USER_WITH_SUCH_EMAIL_ALREADY_EXISTS));

        mockMvc.perform(
                        post("/api/v1/user/")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_WITH_SUCH_EMAIL_ALREADY_EXISTS))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void createUser_UserWithSuchPhoneAlreadyExistsTest() throws Exception{

        UserDto createBody = TestUserDataUtil.createUserDto();
        when(userService.createUser(createBody))
                .thenThrow(new SuchUserAlreadyExistException(USER_WITH_SUCH_PHONE_ALREADY_EXISTS));

        mockMvc.perform(
                        post("/api/v1/user/")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_WITH_SUCH_PHONE_ALREADY_EXISTS))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void updateUser_UserNotFoundTest() throws Exception {

        UserDto updateBody = TestUserDataUtil.createUpdatedUserDto();
        when(userService.updateUser(MOCK_EMAIL,updateBody)).thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/user/" + MOCK_EMAIL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void deleteUser_UserNotFoundTest() throws Exception {

        doThrow(new UserNotFoundException()).when(userService).deleteUser(MOCK_EMAIL);

        mockMvc
                .perform(delete("/api/v1/user/" + MOCK_EMAIL))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void getCurrentRequestByUser_UserNotFoundTest() throws Exception {

        when(requestService.getActiveOrSuspendedRequestByUserEmail(MOCK_EMAIL))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        get("/api/v1/user/" + MOCK_EMAIL + "/requests/current"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void getCurrentRequestByUser_RequestNotFoundTest() throws Exception {

        when(requestService.getActiveOrSuspendedRequestByUserEmail(MOCK_EMAIL))
                .thenThrow(new RequestNotFoundException());

        mockMvc.perform(
                        get("/api/v1/user/" + MOCK_EMAIL + "/requests/current"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(REQUEST_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void closeCurrentRequestByUser_UserNotFoundTest() throws Exception {

        when(requestService.closeRequest(MOCK_EMAIL)).thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/close"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void closeCurrentRequestByUser_RequestNotFoundTest() throws Exception {

        when(requestService.closeRequest(MOCK_EMAIL)).thenThrow(new RequestNotFoundException());

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/close"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(REQUEST_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void activateCurrentRequestByUser_UserNotFoundTest() throws Exception {

        when(requestService.activateRequest(MOCK_EMAIL)).thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/activate"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void activateCurrentRequestByUser_RequestNotFoundTest() throws Exception {

        when(requestService.activateRequest(MOCK_EMAIL)).thenThrow(new RequestNotFoundException());

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/activate"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(REQUEST_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void suspendCurrentRequestByUser_UserNotFoundTest() throws Exception {

        when(requestService.suspendRequest(MOCK_EMAIL)).thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/suspend"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void suspendCurrentRequestByUser_RequestNotFoundTest() throws Exception {

        when(requestService.suspendRequest(MOCK_EMAIL)).thenThrow(new RequestNotFoundException());

        mockMvc.perform(
                        put("/api/v1/user/" + MOCK_EMAIL + "/requests/current/suspend"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(REQUEST_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void addRequest_UserNotFoundTest() throws Exception {

        when(requestService.createRequest(MOCK_EMAIL,MOCK_ID)).thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        post("/api/v1/user/"+ MOCK_EMAIL + "/requests/addRequest/"+ MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void addRequest_RequestNotFoundTest() throws Exception {

        when(requestService.createRequest(MOCK_EMAIL,MOCK_ID)).thenThrow(new RequestNotFoundException());

        mockMvc.perform(
                        post("/api/v1/user/"+ MOCK_EMAIL + "/requests/addRequest/"+ MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(REQUEST_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }
}
