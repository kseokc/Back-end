package icurriculum.admin.config.exception.handler;

import icurriculum.admin.config.exception.AdminGeneralException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdminGlobalExceptionHandler {

    @ExceptionHandler(AdminGeneralException.class)
    public ModelAndView handleAdminGeneralException(AdminGeneralException ex) {
        ModelAndView modelAndView = new ModelAndView("error/errorPage");

        modelAndView.addObject("errorStatus", ex.getErrorStatus());
        modelAndView.addObject("errorStatusMessage", ex.getErrorStatus().getMessage());

        modelAndView.addObject("errorMessage", ex.getMessage());

        return modelAndView;
    }

}
