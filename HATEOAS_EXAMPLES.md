# 🔗 EJEMPLOS DE NAVEGACIÓN HATEOAS - MS-COURSES

## Ejemplo de respuesta con HATEOAS activado

### GET /api/courses/1 (Curso individual)
```json
{
  "id": 1,
  "title": "Introducción a Java",
  "description": "Curso básico de programación en Java",
  "categoryId": 1,
  "managerId": 1,
  "instructorId": 2,
  "publishDate": "2025-01-15",
  "price": 99.99,
  "image": "java-intro.jpg",
  "status": "ACTIVE",
  "_links": {
    "self": {
      "href": "http://localhost:9002/api/courses/1"
    },
    "courses": {
      "href": "http://localhost:9002/api/courses"
    },
    "update": {
      "href": "http://localhost:9002/api/courses/1"
    },
    "delete": {
      "href": "http://localhost:9002/api/courses/1"
    }
  }
}
```

### GET /api/courses (Lista de cursos)
```json
{
  "_embedded": {
    "courseDTOList": [
      {
        "id": 1,
        "title": "Introducción a Java",
        "_links": {
          "self": {
            "href": "http://localhost:9002/api/courses/1"
          },
          "courses": {
            "href": "http://localhost:9002/api/courses"
          },
          "update": {
            "href": "http://localhost:9002/api/courses/1"
          },
          "delete": {
            "href": "http://localhost:9002/api/courses/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:9002/api/courses"
    }
  }
}
```

### GET /api/course-contents/1 (Contenido individual)
```json
{
  "id": 1,
  "courseId": 1,
  "title": "Video Introducción",
  "contentType": "VIDEO",
  "url": "https://example.com/videos/intro.mp4",
  "orderIndex": 1,
  "_links": {
    "self": {
      "href": "http://localhost:9002/api/course-contents/1"
    },
    "all-contents": {
      "href": "http://localhost:9002/api/course-contents"
    },
    "course-contents": {
      "href": "http://localhost:9002/api/course-contents/course/1"
    },
    "update": {
      "href": "http://localhost:9002/api/course-contents/1"
    },
    "delete": {
      "href": "http://localhost:9002/api/course-contents/1"
    }
  }
}
```

### GET /api/course-categories (Lista de categorías)
```json
{
  "_embedded": {
    "courseCategoryDTOList": [
      {
        "id": 1,
        "name": "Programación",
        "description": "Cursos de programación",
        "_links": {
          "self": {
            "href": "http://localhost:9002/api/course-categories/1"
          },
          "categories": {
            "href": "http://localhost:9002/api/course-categories"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:9002/api/course-categories"
    }
  }
}
```
