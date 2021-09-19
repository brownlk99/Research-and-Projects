class Indoctrinator():
   """
   NEEDS TO POST EVERYDAY also posts aren't counted yet
   """
   posts = 0
   todaysPosts = 0

   def __init__(self):
      self.posts = 0

   def setPosts(self, posts):
      self.posts = posts

   def post(self):      #WORKING
      posts = self.posts + 1
      self.setPosts(posts)
      Indoctrinator.posts = self.posts
      Indoctrinator.todaysPosts = Indoctrinator.todaysPosts + 1

   def display(self):
      print()
