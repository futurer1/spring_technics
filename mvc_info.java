@Controller // контроллер, специализированный компонент @Component
public class MyController {
    public MyController() {
    }

    @RequestMapping({"/"}) // end point URL
    public String showFirstView() {
        return "first-view"; // связь с файлом view
    }
}

