package io.github.oliviercailloux.y2018.pangkevin.pangkevin_javaee_travis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("serial")
@WebServlet("additioner/add")
public class AdditionerServlet extends HttpServlet {

	private Integer nm2 = null;
	private final static Logger LOGGER = Logger.getLogger(AdditionerServlet.class.getName());

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
		resp.setContentType(MediaType.TEXT_PLAIN);

		/**
		 * Check si la conversion se passe bien
		 */
		try {
			int num1 = Integer.parseInt(req.getParameter("param1"));
			int num2 = Integer.parseInt(req.getParameter("param2"));
			int res = num1 + num2;
			LOGGER.info("Les valeurs des params suivants sont param1: " + num1 + " et Param2 :" + num2);
			resp.setStatus(200);
			resp.getWriter().println("Résultat :" + res);

		} catch (NumberFormatException e) {
			try {
				/**
				 * Check si l'erreur a été généré par le param1
				 * Donc c'est la param2 qui est faux et regarde si il y a une valeur par défaut
				 */
				int num1 = Integer.parseInt(req.getParameter("param1"));
				if (this.nm2 != null) {
					int num2 = this.nm2;
					int res = num1 + num2;
					LOGGER.info("Le param par défaut est" + this.nm2);
					resp.setStatus(200);
					resp.getWriter().println("Résultat :" + res);
				} else {
					LOGGER.warning("Il n'existe pas de param2 par defaut");
					throw new NumberFormatException("NumberFormatException thrown");
				}

			} catch (NumberFormatException e2) {
				LOGGER.warning("Il y a des valeurs manquantes ou erronnées");
				resp.setStatus(400);
				resp.getWriter().println("Exécution impossible, paramètre manquant.");
			}

		}

	}

	/**
	 * La fonction doPut permet d'insérer la valeur de param2 par defaut
	 */
	@Override

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
		resp.setContentType(MediaType.TEXT_PLAIN);
		/**
		 * Check si la conversion se passe bien
		 */
		try {

			int num2 = Integer.parseInt(req.getParameter("param2"));
			this.nm2 = num2;
			LOGGER.info("La valeur du param2 par defaut est :" + this.nm2);
			resp.getWriter().println("Ok");
		} catch (NumberFormatException e) {

			LOGGER.warning("L'insertion a échoué");
			resp.setStatus(400);
			resp.getWriter().println("Exécution impossible, paramètre manquant.");

		}

	}

}
