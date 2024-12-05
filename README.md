# MicroShop
Hi, this is MicroShop, an shameless copy of the [kabum](https://kabum.com.br) ecommerce.
This project consists on copying the website with an intention of praticing my knowledge in:
 - Java
 - Spring Boot
 - Spring Data JPA
 - MySQL (I've implemented the younger sister MariaDB that is almost the same)
 - Microservice archtecture
 - TTD - Test Driven Development

# Structure of the project
Mainly, we have the following services/subprojects:

 - [ ] ms-catalog
 - [ ] ms-image_store
 - [ ] ms-javawebscraper

And a VUE.JS front-end service.
 - [ ] ms-frontend

## In depth structure of each project:

### ms-javawebscraper
This subproject is an webscraper written from scratch intented to be as fast as possible. Some other options in the avaliable market includes writing scripts to manipulate a live browser such like firefox or chrome for example, but they all are slow due to the rendering and DOM and rendering layers.
Another option is to download the webpage and parse its contents to crawl inside and process all the inner data, storing the retrieved data into an database.
So. This subproject downloads the sitemap of the requested site ([https://kabum.com.br/sitemap.xml](https://kabum.com.br)) and for each inner sitemap, crawls all links inside.

This crawler returns aproximately 74,301 (by the last time I saw) links where 1,198 were classified as category links and 66,888 as product links.

| Type of page | Count |
| ------------ | ----- |
| Category     | 1,198 |
| Product      | 66,888|
| Other pages  | 6,215 |
| All pages    | 74,301|

These "Other pages" includes sales pages, "hotpage" pages, institucional pages, customer service pages, help pages,etc. With the list of categories in hand, we can crawl the categories for more products, filling our database with the right data.

For each category link that has been obtained during our sitemap crawling session, the ms-javawebscraper will concurrently download, crawl and store the categories data into the ms-microshop service using a bot-employee account.

# Running the project.
To run this project, you shoud have:
 - Make
 - Docker (27 or superior)
 - Gradle (8.9 or superior)
 - Java (22 or superior)

Installed in your machine. You need to run:
```bash
make build_all
make run_microshop
```

*TODO: DOCUMENT MORE SECTIONS*
