import http from "@/http-common";

class RecipeDataService {
    getAll(): Promise<any> {
        return http.get("/recipes");
    }
    get(id: any): Promise<any> {
        return http.get(`/recipes/${id}`);
    }
    create(data: any): Promise<any> {
        return http.post("/recipes", data);
    }
    update(id: any, data: any): Promise<any> {
        return http.put(`/recipes/${id}`, data);
    }
    delete(id: any): Promise<any> {
        return http.delete(`/recipes/${id}`);
    }
    deleteAll(): Promise<any> {
        return http.delete(`/recipes`);
    }
    findByNameContaining(name: string): Promise<any> {
        return http.get(`/recipes?name=${name}`);
    }
}
export default new RecipeDataService();
