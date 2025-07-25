package com.example.projectc1023i1.filter;

import com.example.projectc1023i1.component.JwtTokenUtils;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.service.RedisService;
import com.example.projectc1023i1.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
// thang nay dung de phan tich yeu cau cua http
// kiem tra xac thuc voi token dang nhu ve xe thong hanh
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final ServletContext servletContext;
    private final TokenService tokenService;
    private final RedisService redisService;

    /**
     *xu ly yeu cau va tiep tuc bo loc
     *
     * @param request  doi tuong HttpServletRequest duoc gui tu client
     * @param response doi tuong HttpServletResponse duoc  su dung  de gui phan hoi tu server ve client
     * @param filterChain Đối tượng FilterChain cho phép tiếp tục chuỗi các bộ lọc sau khi bộ lọc hiện tại đã hoàn thành xử lý.
     * @throws ServletException Ném ra ngoại lệ này nếu có lỗi xảy ra trong quá trình xử lý yêu cầu.
     * @throws IOException nem ra ngoai le trong qua trinh doc gghi du lieu
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
         try {if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authenticate = request.getHeader("Authorization");
            if (authenticate == null || !authenticate.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return; // Kết thúc xử lý ngay khi lỗi
            }

            final String token = authenticate.substring(7); // lay doan ma token tu chi so 7 den het chuoi
            final String username = jwtTokenUtils.extractUserName(token);
            List<String> tokenList = redisService.getTokenList(username);
            for (String tokenLogout : tokenList ) {
                if (tokenLogout.equals(token)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Token này đã bị chặn. Vui lòng đăng nhập lại.");
                    return;
                }
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Users userDetials = (Users) userDetailsService.loadUserByUsername(username);
                tokenService.getToken(userDetials.getUsername());
                if (jwtTokenUtils.validateToken(token, userDetials)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetials, null, userDetials.getAuthorities()
                    );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unAuthorized");
            return;
        }
    }



        /**
         * kiem tra xem yeu cau co phai bo qua xac thuc hay khong
         * @param request doi tuong HttpServletRequest chua thong tin yeu cau tu Request
         * @return tra ve true neu bo qua token , false neu khong
         */

        private boolean isByPassToken(@NonNull HttpServletRequest request) {

                final List<Pair<String, String >> bypassTokens = Arrays.asList(
                        org.modelmapper.internal.Pair.of("/image/product","GET"),
                        org.modelmapper.internal.Pair.of("/image/deal","GET"),
                        org.modelmapper.internal.Pair.of("/image/collection","GET"),
                        org.modelmapper.internal.Pair.of("/image/user","GET"),

                    org.modelmapper.internal.Pair.of("/api/categories/getAll","GET"),
                    org.modelmapper.internal.Pair.of("/api/categories","GET"),
                    org.modelmapper.internal.Pair.of("/api/auth/google","POST"),
                    org.modelmapper.internal.Pair.of("/api/categories/create","POST"),
                    org.modelmapper.internal.Pair.of("/api/admin/get-all-user","GET"),
                    org.modelmapper.internal.Pair.of("/api/admin/get-infor-employee","GET"),
                    org.modelmapper.internal.Pair.of("/get-all-category-employee","GET"),
                    org.modelmapper.internal.Pair.of("/add-new-employee","POST"),
                    org.modelmapper.internal.Pair.of("/upload-image-employee","POST"),
                    org.modelmapper.internal.Pair.of("/update-employee","PUT"),
                    org.modelmapper.internal.Pair.of("/api/login","POST"),
                    org.modelmapper.internal.Pair.of("/api/register","PUT"),
                    org.modelmapper.internal.Pair.of("/api/send","POST"),
                        org.modelmapper.internal.Pair.of("/api/comment/","GET"),
                        org.modelmapper.internal.Pair.of("/api/save","POST"),
                        org.modelmapper.internal.Pair.of("/api/send-again","POST"),
                        org.modelmapper.internal.Pair.of("/api/payment/create-payment","GET"),
                        org.modelmapper.internal.Pair.of("/api/payment/payment_info","GET"),
                        org.modelmapper.internal.Pair.of("/ws","GET"),
                        org.modelmapper.internal.Pair.of("/ws/","GET"),
                        org.modelmapper.internal.Pair.of("/ws/**","GET")
            );

                for (Pair<String, String > token : bypassTokens) {
                if (request.getServletPath().contains(token.getLeft())
                        && request.getMethod().equals(token.getRight())) {
                    return true;
                }
            }
            return false;
        }
}

