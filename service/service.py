from domain.house import House
from repo.repo import Repo

class Service:
    def __init__(self, repo: Repo, filename: str):
        self.repo = repo
        self.filename = filename
        self.__load_from_file()

    def add_casa(self, casa: House):
        self.repo.add_casa(casa)

    def get_all_case(self):
        return self.repo.get_case()

    def __load_from_file(self):
        case = open(self.filename, 'r')
        for line in case:
            line = line.strip()
            lista_atribute = line.split(", ")
            id = int(lista_atribute[0])
            locatie = str(lista_atribute[1])
            lungime = float(lista_atribute[2])
            latime = float(lista_atribute[3])
            inaltime = float(lista_atribute[4])
            pret = float(lista_atribute[5])
            casa = House(id, locatie, lungime, latime, inaltime, pret)
            self.add_casa(casa)

    def sort_by_price(self):
        lista_case = self.get_all_case()
        lista_case.sort(key=lambda x: x.pret, reverse=True)
        return lista_case
