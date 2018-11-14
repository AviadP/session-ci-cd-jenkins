import sys
import logging
import tornado.ioloop
import tornado.web

from handlers import health


def create_server():
    return tornado.web.Application(
        [(r"/health", health.HealthHandler)],
        compress_response=True
        )


if __name__ == '__main__':
    logger = logging.getLogger("ops-school-lesson-server")
    logger.setLevel(logging.INFO)
    ch = logging.StreamHandler(sys.stdout)
    formatter = logging.Formatter(
        '%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    ch.setFormatter(formatter)
    logger.addHandler(ch)

    logger.info("Starting Server")
    app = create_server()
    app.listen(8000)

    io_loop = tornado.ioloop.IOLoop.current()
    io_loop.start()
