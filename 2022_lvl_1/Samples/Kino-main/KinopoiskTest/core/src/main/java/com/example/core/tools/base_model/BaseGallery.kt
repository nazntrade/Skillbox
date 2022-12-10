package com.example.core.tools.base_model

import com.example.core.tools.all.NestedInfoInCategory

interface BaseGallery: NestedInfoInCategory {
     val imageUrl: String
     val previewUrl: String
}