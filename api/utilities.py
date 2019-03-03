import re
from datetime import datetime
from enum import Enum

from flask.json import JSONEncoder


def snake_to_camel(text):
    """Convert from snake_case to camelCase."""
    return re.sub(r"_(\w)", lambda g: g.groups()[0].upper(), text)


def snake_to_camel_dict(d):
    """Convert snake_case keys to camelCase keys."""
    return {snake_to_camel(k): v for k, v in d.items()}


class CustomJSONEncoder(JSONEncoder):

    def default(self, obj):
        if isinstance(obj, datetime):
            return obj.isoformat(sep=' ')
        elif isinstance(obj, Enum):
            return obj.name
        else:
            return JSONEncoder.default(self, obj)