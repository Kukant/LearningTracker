
from bs4 import BeautifulSoup
import requests as r

urls = [
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/accounting",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/agricultural-science",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/art",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/biology",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/business",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/chemistry",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/design-_and_-communication-graphics",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/economics",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/english",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/french",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/geography",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/history",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/mathematics",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/physics",
    "https://www.studyclix.ie/subjects/leaving-certificate/higher/spanish"
]

import sys
reload(sys)
sys.setdefaultencoding('utf-8')

all_subjects = ""
all_subsections = ""
for url in urls:
    resp = r.get(url)
    bs = BeautifulSoup(resp.text)
    subsections = list(set(x.text for x in bs.findAll("a", {"class": "sx-accordion-header"})))

    try:
        subject_name = bs.find("h3", {"class": ["card-title"]}).text
    except Exception:
        print "Eror " + url
        continue
    all_subjects += "\"{}\", ".format(subject_name)
    subsections_arr = ", ".join("\"{}\"".format(x) for x in subsections)
    all_subsections += "new String[]{{{}}},\n".format(subsections_arr)

print all_subjects
print all_subsections