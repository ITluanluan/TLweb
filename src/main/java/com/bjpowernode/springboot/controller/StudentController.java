package com.bjpowernode.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.springboot.Service.StudentService;
import com.bjpowernode.springboot.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StudentController {
    @Reference
    private StudentService studentService;

    @RequestMapping("/springboot/selectAll")
    public Object selectAll(Model model){
        //准备分页参数 todo

        List<Student> list = studentService.getStudentAll();
        model.addAttribute("studentList", list);
        return "index";
    }


    @RequestMapping("springboot/deleteStudent/{id}")
    public Object deleteStudent(Model model, @PathVariable Integer id){
        int i = studentService.deleteStudentById(id);
        if (i >= 1){
            model.addAttribute("message", "删除成功！");
        }else{

            model.addAttribute("message", "删除失败！");
        }
        return "redirect:/springboot/selectAll";
    }

    @RequestMapping("//springboot/toUpdateStudent/{id}")
    public Object selectStudent(@PathVariable Integer id,Model model){
        Student student = studentService.getStudentById(id);
        System.out.println(student);
        model.addAttribute("student",student);
        return "updatePage";
    }

    @RequestMapping("/springboot/updateStudent")
    public Object updateStudent(Model model,Student student){
        studentService.updateStudent(student);
        return "redirect:/springboot/selectAll";
    }

    @RequestMapping("/springboot/setSession")
    public String setSession(HttpSession session, Model model){
        session.setAttribute("url", "www.baidu.com");
        model.addAttribute("key","放好了");

        return "index";
    }
    @RequestMapping("/springboot/getSession")
    public String getSession(HttpSession session,Model model){
        String url = (String) session.getAttribute("url");
        model.addAttribute("key",url);
        return "good";
    }
}
