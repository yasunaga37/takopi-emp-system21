package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ログイン画面・ログアウト画面への振り分けを行う
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		String action = request.getParameter("action");
		if (action == null ) {
			// ログイン認証画面へ遷移
			url = gotoLogin(request);
		} else if ("logout".equals(action)) {
			// ログアウト画面へ遷移
			url = gotoLogout(request);
		}
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}

	/**
	 * ログイン認証を行い、成功時には従業員一覧画面へ遷移、失敗時にはログイン認証画面へ差し戻す
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン認証
		String url = loginCheck(request, response);
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}

	/**
	 * ログイン認証を行い、成功時には従業員一覧画面へ遷移、失敗時にはログイン認証画面へ差し戻す
	 * @param request
	 * @param response
	 * @return 遷移先URL
	 * @throws ServletException
	 * @throws IOException
	 */
	private String loginCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String url = null;
        // ログイン認証結果を格納する変数
        Boolean check_result = false;
        // ログイン認証用パラメータの取得
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        // ログイン認証
        if(id != null && !id.equals("") && password != null && !password.equals("")) {
        	UserDAO dao = new UserDAO();
        	check_result = dao.loginCheck(id, password);
        }
        // ログイン認証の処理
    	HttpSession session = request.getSession();
        if(!check_result) {
            // 認証できなかったらログイン画面に戻る
            request.setAttribute("_token", session.getId());
            request.setAttribute("hasError", true);
            request.setAttribute("id", id);
            url = "/WEB-INF/views/login/login.jsp";
        } else {
            // 認証できたらログイン状態にして従業員一覧画面へ遷移
            session.setAttribute("login_user", id);
            request.setAttribute("flush", "ログインしました。");
            url = "/employees";
        }
		return url;
	}

	/**
	 * ログイン認証画面へ遷移する
	 * @param request
	 * @return 遷移先URL
	 */
	private String gotoLogin(HttpServletRequest request)  {
        HttpSession session = request.getSession();
        request.setAttribute("_token", session.getId());
        request.setAttribute("hasError", false);
		return "/WEB-INF/views/login/login.jsp";
	}

	/**
	 * session情報を廃棄してトップページへ遷移する
	 * @param request
	 * @return 遷移先URL
	 */
	private String gotoLogout(HttpServletRequest request) {
        // session情報を廃棄してトップページへ遷移
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("flush", "ログアウトしました");
		return "/WEB-INF/views/topPage/index.jsp";
	}

}
