"""
Run from root of repository with:

FLASK_API=api flask run
"""

from flask import Flask
from flask_env import MetaFlaskEnv

from .utilities import CustomJSONEncoder

# Import reverse proxy middleware
from api.reverse_proxied import ReverseProxied


class Configuration(metaclass=MetaFlaskEnv):
    WARCRAFTLOGS_PUBLIC_KEY = None
    WARCRAFTLOGS_PRIVATE_KEY = None
    DEBUG = False
    PORT = 5000


#
# Initialize Application
#
app = Flask(__name__)
app.config.from_object(Configuration)
app.wsgi_app = ReverseProxied(app.wsgi_app)
app.json_encoder = CustomJSONEncoder

#
# Register routes from blueprints
#
from api.blueprints.success import success
app.register_blueprint(success)

#
# Main
#
if __name__ == "__main__":
    app.run(debug=True)
