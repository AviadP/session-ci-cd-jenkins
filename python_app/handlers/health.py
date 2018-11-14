import logging
import tornado.web

logger = logging.getLogger("ops-school-lesson-server")


class HealthHandler(tornado.web.RequestHandler):
    def get(self):
        logger.info("Someone checked our health...")
        self.write("We Healthy!!")
