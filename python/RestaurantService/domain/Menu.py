class Menu:
    def __init__(self, items):
        self.items = items

    @classmethod
    def from_dict(cls, data):
        return cls(items=data['items'])

    def to_dict(self):
        return {'items': self.items}