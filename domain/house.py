class House:
    def __init__(self, id, location, lungime, latime, inaltime, pret):
        self.id = id
        self.location = location
        self.lungime = lungime
        self.latime = latime
        self.inaltime = inaltime
        self.pret = pret


    def get_pret(self):
        return self.pret

    def __str__(self):
        return f'{self.id}. {self.location}, {self.lungime}, {self.latime}, {self.inaltime}, {self.pret}'
