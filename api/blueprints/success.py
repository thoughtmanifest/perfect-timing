import requests
from flask import Blueprint, current_app, jsonify

success = Blueprint("success", __name__)


def get_api_key():
    """Get API key from app config."""
    return current_app.config.get("WARCRAFTLOGS_PUBLIC_KEY")


def reports_uri(guild, server, region, api_key):
    return f"https://www.warcraftlogs.com:443/v1/reports/guild/{guild}/{server}/{region}?api_key={api_key}"


@success.route("/reports/guild/<guild>/<server>/<region>")
def reports(guild, server, region):
    """Give a list of reports."""
    response = requests.get(reports_uri(
        guild, server, region,
        api_key=get_api_key()))
    return jsonify(response.json())


@success.route("/fights/<code>")
def fights(code):
    """
    Fetch the fight data; give it to the model; report to the
    client where/where the data will be ready.
    """
    # TODO: use machine learning
    return code
