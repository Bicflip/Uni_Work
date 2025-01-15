from domain.house import House

class Repo:
    def __init__(self):
        self.lista_case = []

    def add_casa(self, casa):
        self.lista_case.append(casa)

    def get_case(self):
        return self.lista_case