package controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.EmployeeDAO;
import model.dao.GetLicenseDAO;
import model.dao.LicenseDAO;
import model.dao.SectionDAO;
import model.entity.Employee;
import model.entity.GetLicense;
import model.entity.License;
import model.entity.Section;

/**
 * Servlet implementation class EmployeesServlet
 */
@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String url = null;
		// actionによる処理の振り分け
		if (action == null) {
			// 従業員一覧画面へ
			url = gotoEmployeeList(request);
		} else if ("new".equals(action)) {
			// 新規従業員登録画面へ
			url = newEmploee(request);
		} else if ("show".equals(action)) {
			// 従業員情報詳細画面へ
			url = show(request);
		} else if ("edit".equals(action)) {
			// 従業員情報編集画面へ
			url = gotoEdit(request);
		} else if ("delete".equals(action)) {
			// 従業員情報の論理削除実行
			url = delete(request);
		} else if ("del_glicense".equals(action)) {
			// 保有資格削除実行
			url = deleteGetLicense(request);
		} else {
			// do nothing   del_glicense
		}
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String url = null;
		// actionによる処理の振り分け
		if (action == null) {
			// 従業員一覧画面へ
			url = gotoEmployeeList(request);
		} else if ("create".equals(action)) {
			// 新規従業員登録実行
			url = create(request);
		} else if ("update".equals(action)) {
			// 従業員情報編集実行
			url = update(request);
		} else if ("add_license".equals(action)) {
			// 保有資格追加実行
			url = addNewLicense(request);
		}
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * 従業員一覧画面へ遷移する
	 * @param request
	 * @return 遷移先URL
	 */
	private String gotoEmployeeList(HttpServletRequest request) {
		EmployeeDAO dao = new EmployeeDAO();
		List<Employee> list = dao.selectAll();
		request.setAttribute("emp_list", list);
		return "/WEB-INF/views/employees/employee_list.jsp";
	}

	/**
	 * 従業員情報詳細画面へ遷移する
	 * @param request
	 * @return 遷移先URL
	 */
	private String show(HttpServletRequest request)  {
		// 従業員オブジェクト、保有資格情報、及び全資格リストの取得
		String empCode = request.getParameter("emp_code");
		EmployeeDAO eDao = new EmployeeDAO();
		Employee employee = eDao.searchByCode(empCode);
		GetLicenseDAO gDao = new GetLicenseDAO();
		List<GetLicense> gList = gDao.searchByEmployeeCode(empCode);
		LicenseDAO dao = new LicenseDAO();
		List<License> lList = dao.selectAll();
		// requestスコープにデータをセットする
		request.setAttribute("employee", employee);
		request.setAttribute("get_license", gList);
		request.setAttribute("licenses", lList);
		return "/WEB-INF/views/employees/show.jsp";
	}

	/**
	 * 新規従業員登録画面へ遷移する
	 * @param request
	 * @return 遷移先URL
	 */
	private String newEmploee(HttpServletRequest request)  {
		// 部署リストの取得
		SectionDAO sDao = new SectionDAO();
		List<Section> sectionList = sDao.selectAll();
		// 新規従業員に割り当てる従業員コードを取得する
		EmployeeDAO eDao = new EmployeeDAO();
		int maxCode = eDao.getMaxCode();
		String newCode = "E" + (maxCode + 1);
		Employee employee = new Employee();
		employee.setCode(newCode);
		// 必要な情報をrequestスコープに設定
		request.setAttribute("sectionList", sectionList);
		request.setAttribute("employee", employee);
        request.setAttribute("_token", request.getSession().getId());
		return "/WEB-INF/views/employees/new.jsp";
	}

	/**
	 * 新規従業員登録
	 * new.jspから送信されたパラメータを元にして新規従業員登録を行う
	 * @param request
	 * @return 遷移先URL
	 * @throws ParseException
	 */
	private String create(HttpServletRequest request)  {
		String url = null;
		Employee newEmployee = null;
		try {
			newEmployee = setEmployeeData(request);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		EmployeeDAO eDao = new EmployeeDAO();
		int count = eDao.insertNewEmployee(newEmployee);
		if (count == 1) {
			EmployeeDAO dao = new EmployeeDAO();
			List<Employee> list = dao.selectAll();
			String msg = "従業員レコードを" + count + "件追加しました。";
			request.setAttribute("emp_list", list);
			request.setAttribute("flush", msg);
			url = "/WEB-INF/views/employees/employee_list.jsp";
		} else {
			System.out.println("従業員登録に失敗しました。");
		}
		return url;
	}

	/**
	 * 従業員情報編集画面へ遷移する
	 * @param request HttpServletRequest
	 * @return 遷移先URL
	 */
	private String gotoEdit(HttpServletRequest request) {
		String empCode = request.getParameter("emp_code");
		EmployeeDAO eDao = new EmployeeDAO();
		Employee employee = eDao.searchByCode(empCode);
//		System.out.print(employee.getCode() + " " + employee.getLastName() + " " + employee.getFirstName() + " " + employee.getLastKanaName() + " " + employee.getFirstKanaName() + " ");
//		System.out.println(employee.getGender() + " " + employee.getBirthDay() + " " + employee.getSection().getName() + " " + employee.getHireDate() + " " + employee.getUpdate());
		SectionDAO sDao = new SectionDAO();
		List<Section> sectionList = sDao.selectAll();
		request.setAttribute("employee", employee);
		request.setAttribute("sectionList", sectionList);
		return "/WEB-INF/views/employees/edit.jsp";
	}

	/**
	 * 既存の従業員レコードを編集する
	 * @param request HttpServletRequest
	 * @return 遷移先URL
	 */
	private String update(HttpServletRequest request) {
//		System.out.println("update");
		Employee employee = null;
		try {
			employee = setEmployeeData(request);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		EmployeeDAO eDao = new EmployeeDAO();
		int count = eDao.update(employee);
		// 従業員基本情報と保有資格情報を取得
		String code = employee.getCode();
		GetLicenseDAO gDao = new GetLicenseDAO();
		List<GetLicense> gList = gDao.searchByEmployeeCode(code);
		LicenseDAO lDao = new LicenseDAO();
		List<License> lList = lDao.selectAll();
		String msg = null;
		if (count == 1) {
			msg = "従業員情報を更新しました。";
			request.setAttribute("flush", msg);
		} else {
			msg = "従業員情報更新に失敗しました。";
			request.setAttribute("hasError", msg);
			System.out.println("従業員情報更新に失敗しました。");
		}
		// requestスコープにデータをセットする
		Employee e = eDao.searchByCode(code); // 更新後の従業員データを再取得する
		request.setAttribute("employee", e);
		request.setAttribute("get_license", gList);
		request.setAttribute("licenses", lList);
		return "/WEB-INF/views/employees/show.jsp";
	}

	/**
	 * 従業員情報を削除する
	 * @param request HttpServletRequest
	 * @return 遷移先URL
	 */
	private String delete(HttpServletRequest request) {
		String url = null;
		String employeeCode = request.getParameter("code");
//		System.out.println("delete: " + employeeCode);
		EmployeeDAO dao = new EmployeeDAO();
		int count = dao.delete(employeeCode);
		String msg = null;
		if (count == 1) {
			msg = count + "件の従業員レコードを削除しました。";
			request.setAttribute("flush", msg);
			url = gotoEmployeeList(request);
		} else {
			msg = "従業員レコード削除に失敗しました。";
			request.setAttribute("hasError", msg);
			System.out.println("従業員レコード削除に失敗しました。");
		}
		return url;
	}

	private String addNewLicense(HttpServletRequest request) {
		String licenseCode = request.getParameter("get_new_license");
		String employeeCode = request.getParameter("emp_code");
		String strDate = request.getParameter("date");
		java.sql.Date date = Date.valueOf(strDate);
//		System.out.println(licenseCode + " " + employeeCode + " " + strDate);
		GetLicenseDAO gDao = new GetLicenseDAO();
		int count = gDao.insert(licenseCode, employeeCode, date);
		String msg = null;
		if (count == 1) {
			msg = count + "件の保有資格を追加しました。";
			request.setAttribute("flush", msg);
		} else {
			msg = "保有資格追加に失敗しました。";
			request.setAttribute("hasError", msg);
			System.out.println("保有資格追加に失敗しました。");
		}
		return show(request);
	}

	private String deleteGetLicense(HttpServletRequest request) {
//		String url = null;
		String employeeCode = request.getParameter("emp_code");
		String gLicenseCode = request.getParameter("glicense_code");
		System.out.println("employeeCode:" + employeeCode + " " + "gLicenseCode:" + gLicenseCode);
		GetLicenseDAO gDao = new GetLicenseDAO();
		int count = gDao.delete(gLicenseCode, employeeCode);
		return show(request);
	}

	/**
	 * リクエストパラメータを元にemployeeオブジェクトを生成する
	 * @param request HttpServletRequest
	 * @return employee Employee
	 * @throws ParseException
	 */
	private Employee setEmployeeData(HttpServletRequest request) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String code = request.getParameter("code");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String lastKanaName = request.getParameter("lastKanaName");
		String firstKanaName = request.getParameter("firstKanaName");
		int genderNumber = Integer.parseInt(request.getParameter("gender"));
		// 誕生日は入力が必須ではないので、入力されていない場合はnull値を設定する
		java.sql.Date birthDay = null;
		String strBirthDay = request.getParameter("birthDay");
		if (strBirthDay != null && !strBirthDay.equals("")) {
			birthDay = new java.sql.Date(sdf.parse(request.getParameter("birthDay")).getTime());
		}
		// 部署コードから部署オブジェクトを取得する
		String sectionCode = request.getParameter("section");
		SectionDAO sDao = new SectionDAO();
		Section section = sDao.searchByCode(sectionCode);
		// 入社日は入力が必須ではないので、入力されていない場合はnull値を設定する
		java.sql.Date hireDate = null;
		String strhireDate = request.getParameter("hireDate");
		if (strhireDate != null && !strhireDate.equals("")) {
			hireDate = new java.sql.Date(sdf.parse(request.getParameter("hireDate")).getTime());
		}
		String _token = request.getParameter("_token");
//		System.out.print(code + " " + lastName + " " + firstName + " " + lastKanaName + " " + firstKanaName + " ");
//		System.out.println(genderNumber + " " + birthDay + " " + section.getName() + " " + hireDate + " " + _token);
		Employee employee = new Employee(code, lastName, firstName, lastKanaName, firstKanaName, genderNumber, birthDay, section, hireDate);
		return employee;
	}
}
