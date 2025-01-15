#citeste din text file, un get all si o sortare descrescatoare dupa valoarea casei

from domain.house import House
from repo.repo import Repo
from service.service import Service

repo = Repo()
service = Service(repo, "case.txt")

for casa in service.get_all_case():
    print(casa)

print("\n")

case_sortate = service.sort_by_price()
for casa in case_sortate:
    print(casa)
