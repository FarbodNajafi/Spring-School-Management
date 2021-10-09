//package com.example.schoolmanagement;
//
//import com.example.schoolmanagement.controllers.StudentController;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(StudentController.class)
//@AutoConfigureRestDocs(outputDir = "target/snippets")
//public class WebLayerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void shouldReturnOk() throws Exception {
//        mockMvc.perform(get("/"))
//                .andDo(print())
//                .andDo(document("Student", responseFields(
//                        fieldWithPath("message").description("The welcome message for the user.")
//                )))
//                .andExpect(status().isOk());
//    }
//
//}
