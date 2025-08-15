package kdg.programming3.RestaurantApp.presentation.history;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class HistoryInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionHistory sessionHistory;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod()) && modelAndView != null && !request.getRequestURI().startsWith("/webjars")) {
            String url = request.getRequestURI();
            if (request.getQueryString() != null) {
                url += "?" + request.getQueryString();
            }
            sessionHistory.addHistoryItem(new HistoryItem(url));
        }
    }
}