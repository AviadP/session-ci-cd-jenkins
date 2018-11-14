import unittest
import server
from tornado.testing import AsyncHTTPTestCase


class TestHealthHandler(AsyncHTTPTestCase):
    def setUp(self):
        super(TestHealthHandler, self).setUp()

    def get_app(self):
        return server.create_server()

    def test_health_route(self):
        response = self.fetch('/health',
                              method='GET',
                              follow_redirects=False)

        self.assertEqual(response.code, 200)


if __name__ == '__main__':
    unittest.main(failfast=True)
