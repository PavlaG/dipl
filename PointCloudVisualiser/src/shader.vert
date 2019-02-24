#version 120

varying vec3 color;

void main() {
    //color = gl_Color.rgb;
    vec3 vertexPosition = (gl_ModelViewMatrix * gl_Vertex).xyz;
    vec3 lightDirection = normalize(gl_LightSource[0].position.xyz - vertexPosition);
    vec3 surfaceNormal = (gl_NormalMatrix * gl_Normal).xyz;

    float diffuse = max(0, dot(surfaceNormal, lightDirection));
    color.rgb = diffuse * gl_Color.rgb;
    color += gl_LightModel.ambient.rgb;

    vec3 reflectionDir = normalize(reflect(-lightDirection, surfaceNormal));
    float specular = max(0.0, dot(surfaceNormal, reflectionDir));

    if(diffuse != 0) {
        float fspecular = pow(specular, gl_FrontMaterial.shininess);
        //color.rgb += vec3(fspecular, fspecular, fspecular);
    }

    gl_Position = ftransform();
    //gl_Position = vec4(surfaceNormal, 1);
}