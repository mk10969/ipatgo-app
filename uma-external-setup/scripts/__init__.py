# 再起処理の上限を上げる。
import sys
from logging import DEBUG, INFO, config
from pathlib import Path

sys.setrecursionlimit(10000)

log_dir = Path(Path(__file__).parent, "../log")
if not log_dir.exists():
    log_dir.mkdir()

# logging setting
config.dictConfig({
    "version"                 : 1,
    "formatters"              : {
        "simpleFormatter": {
            "format": "%(asctime)s %(process)d %(threadName)s %(name)s %(levelname)s %(message)s"
        },
    },
    "handlers"                : {
        "consoleHandler"    : {
            "class"    : "logging.StreamHandler",
            "level"    : INFO,
            "formatter": "simpleFormatter",
            "stream"   : "ext://sys.stdout"
        },
        "defaultFileHandler": {
            "class"   : "logging.handlers.RotatingFileHandler",
            "filename": f"{log_dir}/access.log",
            "mode"    : "a",
            "maxBytes": 20000 * 1024,
            "encoding": "utf-8",
        },
        "almondFileHandler" : {
            "class"   : "logging.handlers.RotatingFileHandler",
            "filename": f"{log_dir}/jvlink.log",
            "mode"    : "a",
            "maxBytes": 20000 * 1024,
            "encoding": "utf-8",
        }
    },
    "root"                    : {
        "handlers": ["consoleHandler", "defaultFileHandler"],
        "level"   : INFO
    },
    "loggers"                 : {
        "almond": {
            "handlers" : ["consoleHandler", "almondFileHandler"],
            "level"    : DEBUG,
            "propagate": 0
        }
    },
    "disable_existing_loggers": False,
})
